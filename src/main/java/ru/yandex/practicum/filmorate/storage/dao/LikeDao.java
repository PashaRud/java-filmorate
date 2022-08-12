package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

public interface LikeDao {

    void loadLikes(Film film);

    void saveLikes(Film film);
}
