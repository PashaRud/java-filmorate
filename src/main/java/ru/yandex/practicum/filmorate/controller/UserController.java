package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

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


//    @PutMapping
//    public @Valid User update(@Valid  @RequestBody User user) throws WrongParameterException, ValidationException {
//        log.info("Обновлен юзер: " + user);
//        return userService.update(user);
//    }

//    @DeleteMapping
//    public @Valid void delete(@Valid  @RequestBody User user) {
//        log.info("Юзер удален: " + user);
//        userService.deleteUser(user);
//    }

    @GetMapping("{id}")
    public User getUserByID(@PathVariable Integer id) throws WrongParameterException {
        log.info("Найден юзер по id: " + userService.findById(id));
        return userService.findById(id);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addFriends(
            @PathVariable Integer id,
            @PathVariable Integer friendId) {
        log.info("Пользователи с id " + id + " и с id " + friendId + " стали друзьями");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriends(
            @Positive @PathVariable Integer id,
            @Positive @PathVariable Integer friendId) {
        log.info("Пользователи с id " + id + " и с id " + friendId + " больше не друзья");
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("{id}/friends")
    public Collection<User> findAllFriends(
            @Positive @PathVariable Integer id) {
        log.info("Список друзей пользователя с id " + id);
        return userService.getAllUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@Positive @PathVariable Integer id,
                                    @Positive @PathVariable Integer otherId) {
        log.info("Получен список общих друзей у пользователей с id " + id + " и " + otherId);
        return userService.commonFriends(id, otherId);
    }

}

