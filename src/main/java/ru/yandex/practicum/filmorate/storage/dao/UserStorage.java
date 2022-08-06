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

    User create(User user);

    User update(User user);

    boolean containsEmail(String email);

    void loadFriends(User user);

    boolean containsFriendship(Integer filterId1, Integer filterId2, Boolean filterConfirmed);

    void updateFriendship(Integer id1, Integer id2, boolean confirmed,  Integer filterId1, Integer filterId2);

    void insertFriendship(Integer id, Integer friendId);

    void removeFriendship(Integer filterId1, Integer filterId2);
}
