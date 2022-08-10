package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Validator;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Primary
@Slf4j
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final Validator validator;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, Validator validator) {
        this.jdbcTemplate = jdbcTemplate;
        this.validator = validator;
    }

    private User mapToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("USER_ID"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setName(resultSet.getString("NAME"));
        user.setBirthday(resultSet.getDate("BIRTHDAY").toLocalDate());
        return user;
    }

    @Override
    public User findById(Integer id) {
        if(id <= 0) {
            throw new WrongParameterException("Неверный id");
        }
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        List<User> result = jdbcTemplate.query(sql, this::mapToUser, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public void loadFriends(User user) {
        String sql = "(SELECT USER_ID2 ID FROM FRIENDSHIP  WHERE USER_ID1 = ?) " +
                        "UNION " + "(SELECT USER_ID1 ID FROM FRIENDSHIP  WHERE USER_ID2 = ? AND  CONFIRMED = true)";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, user.getId(), user.getId());
        while (sqlRowSet.next()) {
            user.addFriend(sqlRowSet.getInt("id"));
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USERS ORDER BY USER_ID";
        return jdbcTemplate.query(sql, this::mapToUser);
    }

    @Override
    public User create(User user) throws ValidationException {
        validator.userValidator(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO USERS(EMAIL, LOGIN, NAME, BIRTHDAY) values (?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User update(User user)  throws ValidationException, WrongParameterException {
        validator.userValidator(user);
        if(user.getId() <= 0) {
            throw new WrongParameterException("Некорректный id");
        }
        String sql =
                "UPDATE USERS SET LOGIN = ?, EMAIL = ?, NAME = ?, BIRTHDAY = ?" +
                        "WHERE USER_ID = ?";
        jdbcTemplate.update(sql, user.getLogin(), user.getEmail(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    private void testId(Integer id) {
        if (id < 0 || id == null) {
            throw new WrongParameterException("Юзера с id " + id + " нет в БД.");
        }
    }

    @Override
    public List<Integer> getFriendsIds(Integer id) {
        final String sql = "SELECT USER_ID2 FROM FRIENDSHIP WHERE USER_ID1 = ?";
        List<Integer> friends_id = jdbcTemplate.query(sql, (rs, numRow) -> rs.getInt("USER_ID2"), id);
        return friends_id;
    }

    @Override
    public void addFriend(Integer user_id, Integer friend_id) {
        final String sql = "INSERT INTO FRIENDSHIP (USER_ID1, USER_ID2) VALUES (?, ?)";
        jdbcTemplate.update(sql, user_id, friend_id);
    }

    @Override
    public void deleteFriend(Integer user_id, Integer friend_id) {
        final String sql = "DELETE FROM FRIENDSHIP WHERE USER_ID1 = ? AND USER_ID2 = ?";
        jdbcTemplate.update(sql, user_id, friend_id);
    }

//    @Override
    public boolean containsEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE EMAIL = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, email);
        return filmRows.next();
    }

    //ДРУЖБА!!!!!!!!!!!!!!!!!!!!!!!

//    @Override
//    public boolean containsFriendship(Integer filterId1, Integer filterId2, Boolean filterConfirmed) {
//        String sql = "SELECT * FROM FRIENDSHIP WHERE USER_ID1 = ? AND USER_ID2 = ? AND  CONFIRMED = ?";
//        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, filterId1, filterId2, filterConfirmed);
//        return rows.next();
//    }
//
//    @Override
//    public void updateFriendship(Integer id1, Integer id2, boolean confirmed,  Integer filterId1, Integer filterId2) {
//        String sql =
//                "UPDATE FRIENDSHIP SET USER_ID1 = ?, USER_ID2 = ?, CONFIRMED = ? " +
//                        "WHERE USER_ID1 = ? AND USER_ID2 = ?";
//        jdbcTemplate.update(sql, id1, id2, confirmed, filterId1, filterId2);
//    }
//
//    @Override
//    public void insertFriendship(Integer id, Integer friendId) {
//        String sql = "INSERT INTO FRIENDSHIP (USER_ID1, USER_ID2, CONFIRMED) VALUES(?, ?, ?)";
//        jdbcTemplate.update(sql, id, friendId, false);
//    }
//
//    @Override
//    public void removeFriendship(Integer filterId1, Integer filterId2) {
//        String sql = "DELETE FROM FRIENDSHIP WHERE USER_ID1 = ? AND USER_ID2 = ?";
//        jdbcTemplate.update(sql, filterId1, filterId2);
//    }
}