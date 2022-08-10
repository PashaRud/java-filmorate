package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {

    User findById(Integer id);

    List<User> findAll();

    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

    List<User> getFriendsId(Integer id);
    void addFriend(Integer user_id, Integer friend_id);
    void deleteFriend(Integer user_id, Integer friend_id);
}
