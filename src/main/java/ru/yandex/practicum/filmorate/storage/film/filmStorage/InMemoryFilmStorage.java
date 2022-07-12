package ru.yandex.practicum.filmorate.storage.film.filmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Integer, Film> films = new HashMap<>();
    private Integer id = 0;

    @Override
    public Film create(Film film) {
        film.setId(++id);
        films.put(film.getId(), film);
        log.debug("юзер создан - " + film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        log.debug("юзер обновлен " + film.getName());
        return film;
    }

    @Override
    public Film remove(Film film) {
        films.remove(film.getId());
        return film;
    }

    @Override
    public List<Integer> allFilmId() {
        return new ArrayList<>(films.keySet());
    }

    @Override
    public Collection<Film> getAllFilm() {
        log.debug("Количество всех фильмов + " + films.size());
        return films.values();
    }

    @Override
    public Film getFilmById(Integer id) {
        return films.get(id);
    }
}
