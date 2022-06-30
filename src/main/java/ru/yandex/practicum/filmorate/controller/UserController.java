package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private int id = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {

        if (user.getLogin().contains(" ")) {
            log.error("логин не должен содержать пробелы - " + user.getLogin());
            throw new ValidationException("логин не может быть пустым и содержать пробелы.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("некорректная дата рождения - " + user.getBirthday());
            throw new ValidationException("дата рождения не может быть в будущем");
        } else if (user.getName().isEmpty()) {
            log.debug("Имя не указано -> имя соответствует логину " + user.getLogin());
            user.setName(user.getLogin());
        }
        user.setId(++id);
        users.put(user.getId(), user);
        log.info("юзер создан " + user.toString());
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
            if (users.containsKey(user.getId())) {
                if (user.getLogin().contains(" ")) {
                    log.error("логин не должен содержать пробелы - " + user.getLogin());
                    throw new ValidationException("логин не может быть пустым и содержать пробелы.");
                } else if (user.getBirthday().isAfter(LocalDate.now())) {
                    log.error("некорректная дата рождения - " + user.getBirthday());
                    throw new ValidationException("дата рождения не может быть в будущем");
                } else if (user.getName().isEmpty()) {
                    log.info("Имя не указано -> имя соответствует логину " + user.getLogin());
                    user.setName(user.getLogin());
                }
                users.put(user.getId(), user);
                log.debug("юзер обновлен " + user.toString());
            } else {
                log.error("юзера в списке для обновления не существует - " + user.toString());
                throw new ValidationException("Невозможно обновить данные юзера. Такого юзера в списке не существует.");
            }
        return user;
    }
}
