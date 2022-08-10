package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.ArrayList;
import java.util.List;
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
//        validationBeforeUpdate(data);
        return userStorage.update(user);
    }

    public List<User> findAll() {
        List<User> users = userStorage.findAll();
//        users.forEach(userStorage::loadFriends);
        return users;
    }

    public User findById(Integer id) {
        User user = userStorage.findById(id);
        userStorage.loadFriends(user);
        return user;
    }

    //ДРУЗЬЯ!!!!!!!!!!!!!!!

//    public void addFriend(Integer id, Integer friendId) {
//        User user = userStorage.findById(id);
//        User friend = userStorage.findById(friendId);
//        if (user == null || friend == null) {
//            String message = ("Пользователь не найден");
//            log.debug(message);
//            throw new RuntimeException(message);
//        }
//        if (user.containsFriend(friendId)) {
//            log.warn("Друг существует");
//            return;
//        }
//        user.addFriend(friendId);
//        if (userStorage.containsFriendship(friendId, id, false)) {
//            //friendId уже добавил ранее в друзья
//            userStorage.updateFriendship(friendId, id, true, friendId, id);
//        } else if (!userStorage.containsFriendship(id, friendId, null)){
//            //Односторонняя связь, не было дружбы
//            userStorage.insertFriendship(id, friendId);
//        }
//    }
//
//    public void deleteFriend(Integer id, Integer friendId) {
//        User user = this.findById(id);
//        User friend = this.findById(friendId);
//        if (user == null || friend == null) {
//            String message = ("Пользователь не найден");
//            log.warn(message);
//            throw  new RuntimeException(message);
//        }
//        if (!user.containsFriend(friendId)) {
//            log.warn("Друг не существует");
//            return;
//        }
//        user.removeFriend(friendId);
//
//        if (userStorage.containsFriendship(id, friendId, false)) {
//            //Односторонняя связь. friendId не одобрял
//            userStorage.removeFriendship(id, friendId);
//        } else if (userStorage.containsFriendship(id, friendId, true)) {
//            //Совместная связь
//            userStorage.updateFriendship(friendId, id, false, id, friendId);
//        } else if (userStorage.containsFriendship(friendId, id, true)) {
//            //Совместная связь. friendId первый добавил
//            userStorage.updateFriendship(friendId, id, false, friendId, id);
//        }
//    }
//
//    public List<User> getAllUserFriends(Integer userId) {
////        @Valid User user = userStorage.getUserById(userId);
//        if(userStorage.findById(userId) != null) {
//            log.info("Запрос друзей пользователя id " + userId + ".");
//            List<Integer> friendsId = new ArrayList<>(userStorage.findById(userId).getFriends());
//            return friendsId.stream()
//                    .map(id -> userStorage.findById(id))
//                    .collect(Collectors.toList());
//        }
//        log.info("Запрос друзей несуществующего пользователя.");
////        throw new NotFoundException(String.format("Пользователь {} не найден", userId));
//        throw new WrongParameterException (String.format("Пользователь {} не найден", userId));
//    }
//
//    public List<User> commonFriends(Integer userAId, Integer userBId) {
//        return userStorage.findById(userAId).getFriends().stream()
//                .filter(friendId -> userStorage.findById(userBId).getFriends().contains(friendId))
//                .map(friendId -> userStorage.findById(friendId))
//                .collect(Collectors.toList());
//    }

//    public void addFriend(Integer id, Integer friendId){
//        if (userStorage.findById(id) != null && userStorage.findById(friendId)!= null){
//            userStorage.addFriend(id, friendId);
//            log.info("Пользователь " + id + " добавил в друзья id " + friendId + ".");
//        } else {
//            throw new WrongParameterException(String.format("Пользователь не найден"));
//        }
//    }

        public void addFriend(Integer id, Integer friendId) {
        User user = userStorage.findById(id);
        User friend = userStorage.findById(friendId);
        if (user == null || friend == null) {
            String message = ("Пользователь не найден");
            log.debug(message);
            throw new WrongParameterException(message);
        }
        if (user.containsFriend(friendId)) {
            log.warn("Друг существует");
            return;
        }
        user.addFriend(friendId);
//        if (userStorage.containsFriendship(friendId, id, false)) {
//            //friendId уже добавил ранее в друзья
//            userStorage.updateFriendship(friendId, id, true, friendId, id);
//        } else if (!userStorage.containsFriendship(id, friendId, null)){
//            //Односторонняя связь, не было дружбы
//            userStorage.insertFriendship(id, friendId);
//        }
    }

    public void deleteFriend(Integer id, Integer friendId){
        if (userStorage.findById(id) != null && userStorage.findById(friendId) != null){
            userStorage.deleteFriend(id, friendId);
            log.info("Пользователь " + id + " удалил из друзей id " + friendId + ".");
        } else {
            throw new WrongParameterException(String.format("Пользователь не найден"));
        }
    }


//    public List<User> getAllUserFriends (Integer id){ //готово
//        if(userStorage.findById(id) != null) {
//            log.info("Запрос друзей пользователя id " + id + ".");
//            return userStorage.getFriendsIds(id)
//                    .stream()
//                    .map(userStorage::findById)
//                    .collect(Collectors.toList());
//        } else {
//            log.info("Запрос друзей несуществующего пользователя.");
//            throw new WrongParameterException(String.format("Пользователь {} не найден", id));
//        }
//    }

    public List<User> getAllUserFriends(Integer id) {
        User user = this.findById(id);
        if (user == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw new WrongParameterException(message);
        }
        List<Integer> friendsId = user.getFiends();
        List<User> friends = new ArrayList<>();
        for (var friendId : friendsId) {
            friends.add(this.findById(friendId));
        }

        return friends;
    }

//    public List<User> getCommonFriends (Integer firstId, Integer otherId) { //должно так же работать
//        if (userStorage.findById(firstId)!= null && userStorage.findById(otherId)!= null){
//            log.info("Запрос общих друзей пользователей id " + firstId + " и id " +
//                    otherId + ".");
//            List<User> user = getAllUserFriends(firstId);
//            List<User> otherUser = getAllUserFriends(otherId);
//            return user.stream().filter(otherUser::contains).collect(Collectors.toList());
//        } else {
//            log.info("Запрос общих друзей несуществующего пользователя.");
//            throw new WrongParameterException(String.format("Пользователь не найден"));
//        }
//    }

    public List<User> getCommonFriends(Integer id1, Integer id2) {
        User user1 = this.findById(id1);
        User user2 = this.findById(id2);
        if (user1 == null || user2 == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw  new WrongParameterException(message);
        }
        List<Integer> friendsId1 = user1.getFiends();
        List<Integer> friendsId2 = user2.getFiends();
        friendsId1.retainAll(friendsId2);

        List<User> friends = new ArrayList<>();
        for (var friendId : friendsId1) {
            friends.add(this.findById(friendId));
        }

        return friends;
    }
}
