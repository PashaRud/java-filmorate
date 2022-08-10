package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private FilmDao filmStorage;
    private UserService userService;
    private GenreDao genreDao;

    @Autowired
    public FilmService(FilmDao filmStorage,
                       UserService userService,
                       GenreDao genreDao) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.genreDao = genreDao;
    }

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
        List<Film> films = filmStorage.getAllFilm();
        films.forEach(this::loadData);
        log.info("Все фильмы {}", films.size());
        return films;
    }

    public Film findById(Integer id) {
        Film film = filmStorage.getFilmById(id);
        loadData(film);
        log.info("Найден фильм {}", film);
        return film;
    }

    private void loadData(Film film) {
        film.setGenres(genreDao.getGenresByFilm(film));
        filmStorage.loadLikes(film);
    }

    public void addLike(Integer id, Integer userId) {
        Film film = this.findById(id);
        User user = userService.findById(userId);
        film.addLike(userId);
        filmStorage.saveLikes(film);
        log.info("Добавлен лайк");
    }

    public void removeLike(Integer id, Integer userId) {
        Film film = this.findById(id);
        User user = userService.findById(userId);
        film.removeLike(userId);
        filmStorage.saveLikes(film);
        log.info("Удален лайк");
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
