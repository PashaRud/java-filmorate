package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;
import ru.yandex.practicum.filmorate.storage.film.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.film.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;
    private GenreDbStorage genreDbStorage;
    private MpaDbStorage genresDbStorage;

    @Autowired
    public FilmService(FilmDbStorage filmStorage,
                       UserDbStorage userStorage,
                       GenreDbStorage genreDbStorage,
                       MpaDbStorage genresDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreDbStorage = genreDbStorage;
        this.genresDbStorage = genresDbStorage;
    }

    public List<Film> getAll() {
        return filmStorage.getAllFilm();
    }


//    public void addLike (long userId, long filmId){
//        likeDbStorage.addLike(userId, filmId);
//        log.info("Фильм id " + filmId + " получил лайк" + "пользователя " + userId + ".");
//    }
//
//
//    public void deleteLike(long filmId, long userId) {
//        Film film = filmStorage.getFilm(filmId);
//        User user = userStorage.getUser(userId);
//        likeDbStorage.deleteLike(userId, filmId);
//    }
//
//
//    public Film updateFilm(Film film) throws ValidationException {
//        filmStorage.updateFilm(film);
//        genreDbStorage.setFilmGenre(film);
//        genresDbStorage.setFilmMpa(film);
//        return filmStorage.getFilm(film.getId());
//    }
//
//    public Film createFilm(Film film) throws ValidationException {
//        filmStorage.create(film);
//        genreDbStorage.setFilmGenre(film);
//        genresDbStorage.setFilmMpa(film);
//        return filmStorage.getFilm(film.getId());
//    }
}
