package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserDbStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.create(user);
        log.info("Добавлен пользователь {}", user);
        return user;
    }

    public List<User> findAll() {
        List<User> users = userStorage.findAll();
        users.forEach(userStorage::loadFriends);
        return users;
    }

    public User findById(Integer id) {
        User user = userStorage.findById(id);
        userStorage.loadFriends(user);
        return user;
    }



    public List<User> getAllUserFriends(Integer userId) {
//        @Valid User user = userStorage.getUserById(userId);
        List<Integer> friendsId = new ArrayList<>(userStorage.findById(userId).getFriends());
        return  friendsId.stream()
                .map(id -> userStorage.findById(id))
                .collect(Collectors.toList());
    }

    public List<User> commonFriends(Integer userAId, Integer userBId) {
        return userStorage.findById(userAId).getFriends().stream()
                .filter(friendId -> userStorage.findById(userBId).getFriends().contains(friendId))
                .map(friendId -> userStorage.findById(friendId))
                .collect(Collectors.toList());
    }
}
