package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeDao {
    void addLike(long filmId, long userId);
    void deleteLike(long filmId, long userId);
}