package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendsDao;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final FriendsDao friendsDao;

    public User create(User user) throws ValidationException {
        userDao.create(user);
        log.info("Добавлен пользователь {}", user);
        return user;
    }

    public User update(User user) throws ValidationException {
        log.info("Пользователь обновлен {}", user);
        return userDao.update(user);
    }

    public List<User> findAll() {
        List<User> users = userDao.findAll();
        log.info("Все пользователи");
        return users;
    }

    public User findById(Integer id) {
        User user = userDao.findById(id);
        log.info("Поиск пользователя по id {}", user);
        return user;
    }

    public void addFriend(Integer userId, Integer friendId){
        log.info("Добавлен друг с id {}", friendId);
        friendsDao.addFriend(userId, friendId);
    }

    public void deleteFriend(Integer userId, Integer friendId){
        log.info("Удален друг с id {}", friendId);
        friendsDao.deleteFriend(userId, friendId);
    }


    public List<User> getAllUserFriends (Integer id){
        log.info("Все друзья пользователя с id {}", id);
        return friendsDao.getFriendsId(id);
    }

    public List<User> getCommonFriends (Integer user_id, Integer friend_id) {
        log.info("Общие друзья пользователей с id {} и {}", user_id, friend_id);
        return friendsDao.getCommonFriends(user_id, friend_id);
    }
}
