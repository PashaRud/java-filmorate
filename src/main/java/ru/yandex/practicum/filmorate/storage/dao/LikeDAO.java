package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeDAO {
    void addLike(long filmId, long userId);
    void deleteLike(long filmId, long userId);
}