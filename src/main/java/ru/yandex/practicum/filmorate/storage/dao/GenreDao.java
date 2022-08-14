package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreDao {

    Genre findById(Integer id);

    List<Genre> findAll();

    Genre create(Genre genre);

    Genre update(Genre genre);

    Set<Genre> getGenresByFilm(Film film);
}