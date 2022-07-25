package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.transfers.Genre;
import ru.yandex.practicum.filmorate.transfers.MPA;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Set<Integer> likes;
    private Set<Genre> genre;
    private int id;
    @NotNull(message = "Название должно быть не пустым")
    @NotBlank(message = "Название должно быть не пустым")
    private String name;
    @NotNull(message = "Описание должно быть не пустым")
    @NotBlank(message = "Описание должно быть не пустым")
    @Length(max = 200, message = "Длина описания должна быть меньше 200 символов")
    private String description;
    @NotNull(message = "Укажите дату выхода фильма")
    @Past(message = "Фильм еще не вышел")
    private LocalDate releaseDate;
    @Min(1)
    private long duration;

    @NotNull(message = "Укажите возростной рейтинг фильма")
    private MPA mpa;


    //Добавить инициализацию полей MPA и GENRE
    public Film(int id, String name, String description, LocalDate releaseDate, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
        this.genre = new HashSet<>();
    }
}