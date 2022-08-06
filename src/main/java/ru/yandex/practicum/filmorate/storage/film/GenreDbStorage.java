package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.GenreDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Primary
public class GenreDbStorage implements GenreDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findById(Integer id) {
        String sql = "SELECT * FROM GENRES WHERE GENRE_ID = ?";
        List<Genre> result = jdbcTemplate.query(sql, this::mapToGenre, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private Genre mapToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("GENRE_ID"));
        genre.setGenreName(resultSet.getString("NAME"));
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM GENRES ORDER BY GENRE_ID";
        return jdbcTemplate.query(sql, this::mapToGenre);
    }

    @Override
    public Genre create(Genre genre) {
        String sql = "SELECT * FROM GENRES WHERE RATING_ID = ?";
        List<Genre> create = new ArrayList<>(
                jdbcTemplate.query(sql, (rs, rowNum) -> mapToGenre(rs, genre.getId()), genre.getGenreName()));
        return create.get(0);
    }

//    @Override
//    public Genre update(Genre genre) {
//        if (genre.getId() == null) {
//            return null;
//        }
//        String sql = "UPDATE GENRES SET NAME = ? WHERE GENRE_ID = ?";
////        jdbcTemplate.update(sql, genre.setGenreName(), genre.getId());
//        jdbcTemplate.update(sql, genre.setGenreName(), genre.getId());
//        return genre;
//    }

    @Override
    public Genre update(Genre genre) {
        if (genre.getId() == null) {
            return null;
        }
        String sql = "UPDATE GENRES SET GENRE_NAME = ? WHERE GENRE_ID = ?";
        jdbcTemplate.update(sql, genre.getGenreName(), genre.getId());
        return genre;
    }

    @Override
    public Set<Genre> getGenresByFilm(Film film) {
        String sql = "SELECT g.GENRE_ID, g.NAME FROM GENRES g NATURAL JOIN FILMS_GENRES fg WHERE fg.FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql, this::mapToGenre, film.getId()));
    }

}
