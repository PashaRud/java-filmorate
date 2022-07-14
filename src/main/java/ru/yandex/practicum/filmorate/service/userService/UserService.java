package ru.yandex.practicum.filmorate.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService (UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.getAllUser();
    }

    public User update(User user) throws ValidationException {
        if(user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные! Логин содержит пробелы");
        }
        if(user.getId() <= 0) {
            throw new WrongParameterException("Id не может быть отрицательный!");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.update(user);
    }

    public User create(User user) throws ValidationException {
        if (user.getLogin().contains(" ") ) {
            throw new ValidationException("Некорректные данные! Проверьте логин или email");
        }
        if(user.getId() < 0) {
            throw new WrongParameterException("Id не может быть отрицательный!");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User findUserById(Integer id) throws WrongParameterException {
        if (!userStorage.getAllUsersId().contains(id)) {
            throw new WrongParameterException("Некорректные данные! Такого id не существует");
        }
        return userStorage.getUserById(id);
    }

    public void addFriends(User userA, User userB) {
        if (!userStorage.getAllUser().contains(userA) || !userStorage.getAllUser().contains(userB)) {
            throw new WrongParameterException("Неверные входные параметры. Проверьте данные");
        }
        Set<Integer> userList = userA.getFriends();
        userList.add(userB.getId());
        userA.setFriends(userList);

        Set<Integer> friendsList = userB.getFriends();
        friendsList.add(userA.getId());
        userB.setFriends(friendsList);
    }

    public void deleteFriends(User userA, User userB) {
        if (!userStorage.getAllUser().contains(userA) || !userStorage.getAllUser().contains(userB)) {
            throw new WrongParameterException("Неверные входные параметры. Проверьте данные");
        }
        Set<Integer> userList = userA.getFriends();
        userList.remove(userB.getId());
        userA.setFriends(userList);

        Set<Integer> friendsList  = userB.getFriends();
        friendsList.remove(userA.getId());
        userB.setFriends(friendsList);
    }

    public List<User> findAllFriends(User user) {
        List<User> friends = new ArrayList<>();
        List<Integer> friendsId = new ArrayList<>(user.getFriends());
        for (Integer integer : friendsId) {
            friends.add(userStorage.getUserById(integer));
        }
        return  friends;
    }

    public List<User> commonFriends(User userA, User userB) {
        Set<Integer> userAFriends = userA.getFriends();
        Set<Integer> userBFriends = userB.getFriends();
        List<User> commonFriends = new ArrayList<>();

        for (Integer i : userAFriends) {
            for (Integer j : userBFriends) {
                if (i.equals(j)) {
                    commonFriends.add(userStorage.getUserById(i));
                }
            }
        }
        return commonFriends;
    }

    public void deleteUser(User user) {
        userStorage.remove(user);
    }

}
