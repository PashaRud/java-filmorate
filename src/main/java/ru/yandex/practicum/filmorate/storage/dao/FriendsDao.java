package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsDao {

    void addFriend(Integer user_id, Integer friend_id);
    void deleteFriend(Integer user_id, Integer friend_id);
    List<User> getFriendsId(Integer id);
    List<User> getCommonFriends (Integer user_id, Integer friend_id);
}
