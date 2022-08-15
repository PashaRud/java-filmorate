package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeDao {

    void addLike(Integer id, Integer userId);
    void removeLike(Integer id, Integer userId);
}
