package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.dao.LikeDao;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmDao filmStorage;

    private final LikeDao likeDao;

    public Film create(Film film) throws ValidationException  {
        film = filmStorage.create(film);
        filmStorage.createGenreByFilm(film);
        log.info("Добавлен фильма {}", film);
        return film;
    }

    public Film update(Film film) throws ValidationException {
        filmStorage.updateGenre(film);
        film = filmStorage.update(film);
        log.info("Обновлён фильм {}", film);
        return film;
    }

    public List<Film> findAll() {
        return filmStorage.getAllFilm();
    }

    public Film findById(Integer id) {
        Film film = filmStorage.getFilmById(id);
        log.info("Найден фильм {}", film);
        return film;
    }

    public List<Film> findPopularFilms(int count) {
        return filmStorage.findPopularFilms(count);
    }

    public void addLike(Integer id, Integer userId) {
        likeDao.addLike(id, userId);
    }

    public void removeLike(Integer id, Integer userId) {
        likeDao.removeLike(id, userId);
    }
}
