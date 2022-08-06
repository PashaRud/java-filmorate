package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class MpaDbStorage implements MpaDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa findById(int id) {
        String sql = "SELECT * FROM RATINGS WHERE RATING_ID = ?";
        List<Mpa> result = jdbcTemplate.query(sql, this::mapToRating, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private Mpa mapToRating(ResultSet resultSet, int rowNum) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(resultSet.getInt("RATING_ID"));
        mpa.setName(resultSet.getString("NAME"));
        return mpa;
    }

    @Override
    public List<Mpa> findAll() {
        String sql = "SELECT * FROM RATINGS ORDER BY RATING_ID";
//        return jdbcTemplate.query(sql, (rs, rowNum) -> mapToRating(rs, rowNum));
        return jdbcTemplate.query(sql, this::mapToRating);
    }

    @Override
    public Mpa create(Mpa mpa) {
        String sql = "SELECT * FROM MPA WHERE RATING_ID = ?";
        List<Mpa> create = new ArrayList<>(
                jdbcTemplate.query(sql, (rs, rowNum) -> mapToRating(rs, mpa.getId()), mpa.getName()));
        if (create.isEmpty()) {
            return null;
        }
        return create.get(0);
    }

    @Override
    public Mpa update(Mpa mpa) {
        String sql = "UPDATE RATINGS SET NAME = ? WHERE RATING_ID = ?";
        jdbcTemplate.update(sql, mpa.getName(), mpa.getId());
        return mpa;
    }
}
