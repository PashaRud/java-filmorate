package ru.yandex.practicum.filmorate.storage.dao;

import java.util.Collection;

public interface FriendshipDao {

    Collection<Long> getFriendsIds(Long id);

    void addFriend(Long user_id, Long friend_id);

    void deleteFriend(Long user_id, Long friend_id);
}