package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserStorage {

    User findById(Integer id);

    List<User> findAll();

    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

//    boolean containsEmail(String email);

    List<User> getFriendsId(Integer id);
    void addFriend(Integer user_id, Integer friend_id);
    void deleteFriend(Integer user_id, Integer friend_id);
//    List<User> getAllUserFriends(Integer id);
}
