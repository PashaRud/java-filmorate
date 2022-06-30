package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {

    private int id;
    @NotNull(message = "Название должно быть не пустым")
    @NotBlank(message = "Название должно быть не пустым")
    private String name;
    @Size(max = 200, message = "Длина описания должна быть меньше 200 символов")
    private String description;
    private LocalDate releaseDate;
//    @DurationMin(seconds = 0, message = "Длина фильма должна быть больше 0")
    @Positive(message = "Длина не может быть отрицательной")
    private int duration;
}
