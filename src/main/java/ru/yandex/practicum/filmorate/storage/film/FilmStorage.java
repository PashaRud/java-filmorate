package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Film remove(Film film);

    List<Integer> allFilmId();

    Collection<Film> getAllFilm();

    Film getFilmById(Integer id);


}
