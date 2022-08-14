package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {

    User findById(Integer id);

    List<User> findAll();

    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;
}
