package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.userService.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Текущее количество юзеров: ", + userService.findAll().size());
        return userService.findAll();
    }

    @PostMapping
    public @Valid User create(@Valid @RequestBody User user) throws ValidationException {
        log.info("Добавлен юзер: " + user);
        return userService.create(user);
    }


    @PutMapping
    public @Valid User update(@Valid  @RequestBody User user) throws WrongParameterException, ValidationException {
        log.info("Обновлен юзер: " + user);
        return userService.update(user);
    }

    @DeleteMapping
    public @Valid void delete(@Valid  @RequestBody User user) {
        log.info("Юзер удален: " + user);
        userService.deleteUser(user);
    }

    @GetMapping("{id}")
    public User getUserByID(@PathVariable Integer id) throws WrongParameterException {
        log.info("Найден юзер по id: " + userService.findUserById(id));
        return userService.findUserById(id);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addFriends(
            @PathVariable Integer id,
            @PathVariable Integer friendId) {
        log.info("Пользователи с id " + id + " и с id " + friendId + " стали друзьями");
        userService.addFriends(userService.findUserById(id), userService.findUserById(friendId));
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriends(
            @PathVariable Integer id,
            @PathVariable Integer friendId) {
        log.info("Пользователи с id " + id + " и с id " + friendId + " больше не друзья");
        userService.deleteFriends(userService.findUserById(id), userService.findUserById(friendId));
    }

    @GetMapping("{id}/friends")
    public Collection<User> findAllFriends(
            @PathVariable Integer id) {
        log.info("Список друзей пользователя с id " + id);
        return userService.findAllFriends(userService.findUserById(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable Integer id,
                                    @PathVariable Integer otherId) {
        log.info("Получен список общих друзей у пользователей с id " + id + " и " + otherId);
        return userService.commonFriends(userService.findUserById(id),userService.findUserById(otherId));
    }

}