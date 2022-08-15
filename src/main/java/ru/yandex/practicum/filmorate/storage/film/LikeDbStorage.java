package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.storage.dao.LikeDao;

import java.util.Set;

@RequiredArgsConstructor
@Component
@Primary
public class LikeDbStorage implements LikeDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(Integer id, Integer userId) {
        String sqlQuery = "MERGE INTO FILMS_LIKES (FILM_ID, USER_ID) values (?, ?)";
        jdbcTemplate.update(sqlQuery, id, userId);
    }
    @Override
    public void removeLike(Integer id, Integer userId) {
        boolean testPair;
        String sql = "SELECT EXISTS(SELECT * FROM FILMS_LIKES WHERE FILM_ID = ? AND USER_ID = ?)";
        testPair = Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id, userId));
        if (!testPair) {
            throw new WrongParameterException("Ошибочный входные данные");
        }
        String sqlQuery = "DELETE FROM FILMS_LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sqlQuery, id, userId);
    }
}
