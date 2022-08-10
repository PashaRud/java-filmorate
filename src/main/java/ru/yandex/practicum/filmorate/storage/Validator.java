package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Component
public class Validator {


    public void userValidator(User user) throws ValidationException {
        if(user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный адрес email");
        }
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("Некорректная дата Дня Рождения");
        }
        if(user.getLogin().contains(" ")){
            throw new ValidationException("Некорректный логин. Логин не может содержать пробелы");
        }
        if (user.getName().isBlank()||user.getName() == null){
            user.setName(user.getLogin());
        }
    }

    public void filmValidator(Film film) throws ValidationException {
        if(film.getName().isBlank() || film.getName() == null){
            throw new ValidationException("Некорректное название фильма. Название не может быть пустым");
        }
        if(film.getDescription().length() > 200){
            throw new ValidationException("Некорректное описание фильма. " +
                    "Описание не может содержать более 200 символов");
        }
        if(film.getDuration() <= 0){
            throw new ValidationException("Некорректная продолжительность. Продолжительность не может быть меньше 0");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Невозможно добавить фильм с датой релиза до 28.12.1985 г.");

        if(film.getMpa() == null){
            throw new ValidationException("Некорректный возрастной рейтинг. Добавьте возрастной рейтинг");
        }
    }
}