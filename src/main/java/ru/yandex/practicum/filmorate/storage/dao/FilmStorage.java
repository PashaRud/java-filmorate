package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.data.relational.core.sql.Like;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException;

    void remove(Film film) throws ValidationException;

//    List<Integer> allFilmId();

    List<Film> getAllFilm();

    Film getFilmById(Integer id);

    void createGenreByFilm(Film film) throws ValidationException;

    void updateGenre(Film film) throws ValidationException;

    void loadLikes(Film film);

    void saveLikes(Film film);

    void deleteLike(Integer filmId, Integer userId);

}
