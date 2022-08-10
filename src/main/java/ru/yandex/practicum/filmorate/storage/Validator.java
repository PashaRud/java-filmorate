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
            throw new ValidationException("Ошибка ввода электронной почты");
        }
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if(user.getLogin().contains(" ")){
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if (user.getName().isBlank()||user.getName() == null){
            user.setName(user.getLogin());
        }
    }

    public void filmValidator(Film film) throws ValidationException {
        if(film.getName().isBlank() || film.getName() == null){
            throw new ValidationException("Название не может быть пустым.");
        }
        if(film.getDescription().length() > 200){
            throw new ValidationException("Описание не может содержать более 200 символов.");
        }
        if(film.getDuration() <= 0){
            throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Невозможно добавить фильм с датой релиза ранее 28.12.1985 г.");

        if(film.getMpa() == null){
            throw new ValidationException("Невозможно добавить фильм без возрастного рейтинга");
        }
    }
}