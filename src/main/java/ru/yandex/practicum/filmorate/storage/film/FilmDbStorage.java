package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.Validator;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;
    private final Validator validator;

    private Film mapToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("FILM_ID"));
        film.setName(resultSet.getString("NAME"));
        film.setDescription(resultSet.getString("DESCRIPTION"));
        film.setReleaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate());
        film.setDuration(resultSet.getInt("DURATION"));
        film.setMpa(new Mpa(resultSet.getInt("RATING_ID"), resultSet.getString("R_NAME")));
        return film;
    }

    @Override
    public Film create(Film film) throws ValidationException {
        validator.filmValidator(film);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("FILM_ID");

        Map<String, Object> values = new HashMap<>();
        values.put("NAME", film.getName());
        values.put("DESCRIPTION", film.getDescription());
        values.put("RELEASE_DATE", film.getReleaseDate());
        values.put("DURATION", film.getDuration());
        values.put("RATING_ID", film.getMpa().getId());

        film.setId(simpleJdbcInsert.executeAndReturnKey(values).intValue());
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        if(film.getId() <= 0) {
            throw new WrongParameterException("Некорретный id");
        }
        validator.filmValidator(film);
        String sql =
                "UPDATE FILMS SET NAME= ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING_ID = ? " +
                        "WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        return film;
    }

    @Override
    public List<Film> getAllFilm() {
        String sql = "SELECT f.FILM_ID, f.NAME, f.DESCRIPTION, f.RATING_ID, r.NAME RATINGS, " +
                "f.RELEASE_DATE, f.DURATION, fg.GENRE_ID AS GENRE_ID, g.NAME GENRE " +
                "FROM FILMS f " +
                "JOIN RATINGS r on r.RATING_ID = f.RATING_ID " +
                "LEFT JOIN FILMS_GENRES fg ON f.FILM_ID = fg.FILM_ID " +
                "LEFT JOIN GENRES g ON g.GENRE_ID = fg.GENRE_ID ";
        return jdbcTemplate.query(sql, this::mapToFilm);
    }

    @Override
    public Film getFilmById(Integer id) {
        String sql = "SELECT f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.RATING_ID, r.NAME R_NAME " +
                        "FROM FILMS f JOIN RATINGS r ON f.RATING_ID = r.RATING_ID " +
                        "WHERE f.FILM_ID = ?";
        List<Film> result = jdbcTemplate.query(sql, this::mapToFilm, id);
        if (result.isEmpty()) {
            throw new WrongParameterException("Такого фильма не существует");
        }
        Set<Genre> genres = genreDao.getGenresByFilm(result.get(0));
        result.get(0).setGenres(genres);
        return result.get(0);
    }

    @Override
    public List<Film> findPopularFilms(Integer count) {
        String sqlQuery = "SELECT f.FILM_ID FROM FILMS f " +
                "LEFT JOIN FILMS_LIKES fl on f.FILM_ID = fl.FILM_ID " +
                "GROUP BY f.FILM_ID " +
                "ORDER BY COUNT(fl.USER_ID) " +
                "DESC LIMIT ?";
        List<Integer> popularFilmIds = jdbcTemplate.queryForList(sqlQuery, Integer.class, count);
        List<Film> popFilms = popularFilmIds.stream().map(this::getFilmById).collect(Collectors.toList());
        return popFilms;
    }

    @Override
    public void createGenreByFilm(Film film) {
        String sql = "INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID) VALUES(?, ?)";
        Set<Genre> genres = film.getGenres();
        if (genres == null) {
            return;
        }
        for (Genre genre : genres ) {
            jdbcTemplate.update(sql, film.getId(), genre.getId());
        }
    }

    @Override
    public void updateGenre(Film film) {
        String sql = "DELETE FROM FILMS_GENRES WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, film.getId());
        createGenreByFilm(film);
    }
}