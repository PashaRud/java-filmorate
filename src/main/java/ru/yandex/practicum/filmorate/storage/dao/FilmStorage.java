package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.data.relational.core.sql.Like;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

//    Film remove(Film film);

//    List<Integer> allFilmId();

    List<Film> getAllFilm();

    Film getFilmById(Integer id);

    void createGenre(Film film);

    void updateGenre(Film film);
//
//    void loadLikes(Film film);
//
//    void saveLikes(Film film);
}
