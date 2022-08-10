package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
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
    public @Valid User update(@Valid  @RequestBody User user) throws ValidationException {
        log.info("Обновлен юзер: " + user);
        return userService.update(user);
    }

    @GetMapping("{id}")
    public User getUserByID(@PathVariable Integer id) {
        log.info("Найден юзер по id: " + userService.findById(id));
        return userService.findById(id);
    }


    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable Integer id) {
        return userService.getAllUserFriends(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id,
                          @PathVariable Integer friendId) {
        System.out.println("START addFriend in controller");
        userService.addFriend(id, friendId);
        System.out.println("END addFriend in controller");
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id,
                             @PathVariable Integer friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Integer id,
                                             @PathVariable Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}

