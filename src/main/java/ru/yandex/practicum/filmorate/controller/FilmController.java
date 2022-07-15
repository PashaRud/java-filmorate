package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.filmService.FilmService;
import ru.yandex.practicum.filmorate.service.userService.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmService filmService;
    UserService userService;
    @Autowired
    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
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

    @DeleteMapping
    public @Valid void delete(@Valid @RequestBody Film film) throws WrongParameterException {
        log.info("Фильм удален: " + film);
        filmService.deleteFilm(film);
    }

    @GetMapping("{id}")
    public Film getFilmById(@PathVariable Integer id) throws WrongParameterException, ValidationException {
        log.info("Найден фильм по id: " + filmService.findFilmById(id));
        return filmService.findFilmById(id);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Integer id,
                        @PathVariable Integer userId) throws ValidationException {
        log.info("Фильму с id " + id + " поставлен лайк пользователем с id " + userId);
        filmService.addLike(filmService.findFilmById(id), userService.findUserById(userId));
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id,
                           @PathVariable Integer userId) throws ValidationException {
        log.info("Фильму с id " + id + " удален лайк пользователем с id " + userId);
        filmService.deleteLike(filmService.findFilmById(id), userService.findUserById(userId));
    }

    @GetMapping("/popular")
    public Collection<Film> getMostPopularFilms(
            @RequestParam(defaultValue = "10",required = false) Integer count) {
        log.info("Список наиболее полпулярных фильмов в количестве " + count);
        return filmService.getPopularFilms(count);

    }
}