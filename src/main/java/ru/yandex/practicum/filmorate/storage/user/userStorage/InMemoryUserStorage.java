package ru.yandex.practicum.filmorate.storage.user.userStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    @Override
    public User create(User user) {
            user.setId(++id);
            users.put(user.getId(), user);
            log.debug("юзер создан - " + user.getName());
            return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        log.debug("юзер обновлен " + user.getName());
        return user;
    }

    @Override
    public User remove(User user) {
        users.remove(user.getId());
        return user;
    }

    @Override
    public List<Integer> getAllUsersId() {
        return new ArrayList<>(users.keySet());
    }

    @Override
    public List<User> getAllUser() {
        log.debug("Количество всех юзеров + " + users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Integer id) {
        return users.get(id);
    }
}
