package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {

    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException;

    List<Film> getAllFilm();

    Film getFilmById(Integer id);

    List<Film> findPopularFilms(Integer count);

    void createGenreByFilm(Film film) throws ValidationException;

    void updateGenre(Film film) throws ValidationException;
}
