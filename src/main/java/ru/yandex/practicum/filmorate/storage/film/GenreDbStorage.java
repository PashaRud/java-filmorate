package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Primary
public class GenreDbStorage implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findById(Integer id) {
        if (id <= 0) {
            throw new WrongParameterException("Некорректный id!");
        }
        String sql = "SELECT * FROM GENRES WHERE GENRE_ID = ?";
        List<Genre> result = jdbcTemplate.query(sql, this::mapToGenre, id);
        return result.get(0);
    }

    private Genre mapToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("GENRE_ID"));
        genre.setName(resultSet.getString("NAME"));
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM GENRES ORDER BY GENRE_ID";
        List<Genre> genres = jdbcTemplate.query(sql, this::mapToGenre);

        return genres;
    }

    @Override
    public Genre create(Genre genre) {
        String sql = "SELECT * FROM GENRES WHERE RATING_ID = ?";
        List<Genre> create = new ArrayList<>(
                jdbcTemplate.query(sql, (rs, rowNum) -> mapToGenre(rs, genre.getId()), genre.getName()));
        return create.get(0);
    }


    @Override
    public Genre update(Genre genre) {
        if (genre.getId() == null) {
            return null;
        }
        if(genre.getId() <= 0) {
            throw new WrongParameterException("некорректный id");
        }
        String sql = "UPDATE GENRES SET NAME = ? WHERE GENRE_ID = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());
        return genre;
    }

    @Override
    public Set<Genre> getGenresByFilm(Film film) {
        String sql = "SELECT g.GENRE_ID, g.NAME FROM GENRES g NATURAL JOIN FILMS_GENRES fg WHERE fg.FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql, this::mapToGenre, film.getId()));
    }
}
