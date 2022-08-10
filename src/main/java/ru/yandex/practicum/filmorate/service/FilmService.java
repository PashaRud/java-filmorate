package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;
import ru.yandex.practicum.filmorate.storage.film.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.film.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserService userService;
    private GenreDao genreDao;
    private MpaDbStorage mpaDbStorage;

    private GenreDbStorage genreDbStorage;
//    private MpaDao genresDbStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage,
                       UserService userService,
                       GenreDao genreDao,
                       GenreDbStorage genreDbStorage,
                       MpaDbStorage mpaDbStorage) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.genreDao = genreDao;
        this.genreDbStorage = genreDbStorage;
        this.mpaDbStorage = mpaDbStorage;
    }

//    @Override
    public Film create(Film film) throws ValidationException  {
        film = filmStorage.create(film);
        filmStorage.createGenreByFilm(film);
        log.info("Добавлен фильма {}", film);
        return film;
    }

//    @Override
    public Film update(Film film) throws ValidationException {
        film = filmStorage.update(film);
        filmStorage.updateGenre(film);
        log.info("Обновлён фильм {}", film);
        return film;
    }

//    @Override
    public List<Film> findAll() {
        List<Film> films = filmStorage.getAllFilm();
        films.forEach(this::loadData);
        return films;
    }

//    @Override
    public Film findById(Integer id) {
        Film film = filmStorage.getFilmById(id);
        loadData(film);
        return film;
    }

    private void loadData(Film film) {
        film.setGenre(genreDao.getGenresByFilm(film));
        filmStorage.loadLikes(film);
    }

    public void addLike(Integer id, Integer userId) {
        Film film = this.findById(id);
        User user = userService.findById(userId);
//        validateLike(film, user);
        film.addLike(userId);
        filmStorage.saveLikes(film);
    }

    public void removeLike(Integer id, Integer userId) {
        Film film = this.findById(id);
        User user = userService.findById(userId);
        film.removeLike(userId);
        filmStorage.saveLikes(film);
    }

    public List<Film> findPopularMovies(int count) {
        List<Film> films = this.findAll();
        films.sort(Comparator.comparing(Film::getLikesCount).reversed());
        if(count > films.size()) {
            count = films.size();
        }

        return new ArrayList<>(films.subList(0, count));
    }

}
