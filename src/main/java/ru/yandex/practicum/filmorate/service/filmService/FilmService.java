package ru.yandex.practicum.filmorate.service.filmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.WrongParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    private final LocalDate FIRST_MOVIE = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService (FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.getAllFilm();
    }

    public Film create(Film film) throws ValidationException {
        if (filmStorage.getAllFilm().contains(film)) {
            throw new ValidationException("Фильм с Id " + film.getId() + " уже существует.");
        }
        if (film.getReleaseDate().isBefore(FIRST_MOVIE) || film.getDuration() < 0) {
            throw new ValidationException("Некорректные данные! Дата релиза должна быть позднее 28.12.1985 года!");
        }
        if (film.getId() < 0) {
            throw new WrongParameterException("Id не может быть отрицательный!");
        }
        return filmStorage.create(film);
    }

    public Film update(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(FIRST_MOVIE) || film.getDuration() < 0) {
            throw new WrongParameterException("" + "Некорректные данные!");
        }
        if (film.getId() < 0) {
            throw new WrongParameterException("Id не может быть отрицательный!");
        }
        return filmStorage.update(film);
    }

    public Film findFilmById(Integer id) {
        if (!filmStorage.allFilmId().contains(id)) {
            throw new WrongParameterException("Некорректные данные! Проверьте логин или email");
        }
        return filmStorage.getFilmById(id);
    }

    public void addLike(Film film, User user) {
        Set<Integer> filmLikes = film.getLikes();
        filmLikes.add(user.getId());
        film.setLikes(filmLikes);
    }

    public void deleteLike(Film film, User user) {
        Set<Integer> filmLikes = film.getLikes();
        filmLikes.remove(user.getId());
        film.setLikes(filmLikes);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.getAllFilm().stream()
                .sorted((filmA, filmB) -> filmB.getLikes().size() - filmA.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }

    public void DeleteFilm(Film film) {
        filmStorage.remove(film);
    }


}