package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserDbStorage userStorage;

    @Autowired
    public UserService(UserDbStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.create(user);
        log.info("Добавлен пользователь {}", user);
        return user;
    }

    public User update(User user) throws ValidationException {
        log.info("Пользователь обновлен {}", user);
        return userStorage.update(user);
    }

    public List<User> findAll() {
        List<User> users = userStorage.findAll();
        log.info("Все пользователи");
        return users;
    }

    public User findById(Integer id) {
        User user = userStorage.findById(id);
        log.info("Поиск пользователя по id {}", user);
        return user;
    }

    public void addFriend(Integer userId, Integer friendId){
        if ((userStorage.findAll().stream().noneMatch(u -> Objects.equals(u.getId(), userId)))
                || (userStorage.findAll().stream().noneMatch(u -> Objects.equals(u.getId(), friendId)))) {
            throw new WrongParameterException("user.id или friend.id не найден");
        }
        log.info("Добавлен друг с id {}", friendId);
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Integer userId, Integer friendId){
        log.info("Удален друг с id {}", friendId);
        userStorage.deleteFriend(userId, friendId);
    }


    public List<User> getAllUserFriends (Integer id){
        log.info("Все друзья пользователя с id {}", id);
        return userStorage.getFriendsId(id);
    }

    public List<User> getCommonFriends (Integer user_id, Integer friend_id) {
        List<User> user = getAllUserFriends(user_id);
        List<User> otherUser = getAllUserFriends(friend_id);
        return user.stream().filter(otherUser::contains).collect(Collectors.toList());
    }
}
