package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Текущее количество фильмов: ", + filmService.findAll().size());
        return filmService.findAll();
    }

    @PostMapping
    public @Valid Film create(@Valid @RequestBody Film film) throws WrongParameterException, ValidationException {
        log.info("Добавлен фильм: " + film);
        return filmService.create(film);
    }

    @PutMapping
    public @Valid Film update(@Valid @RequestBody Film film) throws WrongParameterException, ValidationException {
        log.info("Обновлен фильм: " + film);
        return filmService.update(film);
    }

    @GetMapping("{id}")
    public Film getFilmById(@PathVariable Integer id) throws WrongParameterException {
        log.info("Найден фильм по id: " + filmService.findById(id));
        return filmService.findById(id);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Integer id,
                        @PathVariable Integer userId) {
        log.info("Фильму с id " + id + " поставлен лайк пользователем с id " + userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id,
                           @PathVariable Integer userId) {
        log.info("Фильму с id " + id + " удален лайк пользователем с id " + userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getMostPopularFilms(
            @RequestParam(defaultValue = "10") Integer count) {
        log.info("Список наиболее популярных фильмов в количестве " + count);
        return filmService.findPopularFilms(count);
    }
}