package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {
    private Set<Integer> likes;
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
    public Film(int id, String name, String description, LocalDate releaseDate, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
    }
}