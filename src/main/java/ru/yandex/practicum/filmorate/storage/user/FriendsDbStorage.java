package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendsDao;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Primary
public class FriendsDbStorage implements FriendsDao {

    private final UserDao userDao;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Integer id, Integer friendId) {
        if ((userDao.findAll().stream().noneMatch(u -> Objects.equals(u.getId(), id)))
                || (userDao.findAll().stream().noneMatch(u -> Objects.equals(u.getId(), friendId)))) {
            throw new WrongParameterException("user.id или friend.id не найден");
        }
        String sqlQuery = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public void deleteFriend(Integer user_id, Integer friend_id) {
        final String sql = "DELETE FROM FRIENDSHIP WHERE USER_ID = ? AND friend_id = ?";
        jdbcTemplate.update(sql, user_id, friend_id);
    }

    @Override
    public List<User> getFriendsId(Integer id) {
        List<Integer> friendIds = jdbcTemplate.queryForList("SELECT friend_id FROM friendship WHERE user_id = ?",
                Integer.class, id);
        List<User> list = new ArrayList<>();
        for (Integer friendId : friendIds) {
            User userById = userDao.findById(friendId);
            list.add(userById);
        }
        return list;
    }

    public List<User> getCommonFriends (Integer user_id, Integer friend_id) {
        List<User> user = getFriendsId(user_id);
        List<User> otherUser = getFriendsId(friend_id);
        return user.stream().filter(otherUser::contains).collect(Collectors.toList());
    }
}
