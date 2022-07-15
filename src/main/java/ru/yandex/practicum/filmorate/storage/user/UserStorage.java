package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserStorage {

    User create(User user);

    User update(User user);

    User remove(User user);

    List<Integer> getAllUsersId();

    List<User> getAllUser();

    User getUserById(Integer id);
}
