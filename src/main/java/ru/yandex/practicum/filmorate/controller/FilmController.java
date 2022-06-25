package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController  {

    private final LocalDate FIRST_MOVIE = LocalDate.of(1895, 12, 28);
    private int id = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {

        if (film.getReleaseDate().isBefore(FIRST_MOVIE)) {
            log.error("некорректная дата выхода фильма - " + film.getReleaseDate());
            throw new ValidationException("Некорректная дата выхода кино. Первое кино было выпущено 28.12.1895");
        } else {
            film.setId(++id);
            films.put(film.getId(), film);
            log.info("Фильм создан " + film.toString());
            return film;
        }
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
            if (films.containsKey(film.getId())) {
                if (film.getReleaseDate().isBefore(FIRST_MOVIE)) {
                    log.error("некорректная дата выхода фильма - " + film.getReleaseDate());
                    throw new ValidationException("Некорректная дата выхода кино. Первое кино было выпущено 28.12.1895");
                } else {
                    films.put(film.getId(), film);
                    log.info("фильм обновлен " + film.toString());
                }
            } else {
                log.error("фильма в списке для обновления не существует - " + film.toString());
                throw new ValidationException("Невозможно обновить фильм. Такого фильма в списке не существует.");
            }
        return film;
    }
}
