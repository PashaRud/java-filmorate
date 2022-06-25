package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class User {

    private int id;
    @NotNull(message = "не указан email")
    @NotBlank(message = "не указан email")
    @Email(message = "некорректный формат email-а")
    private String email;
    @NotNull(message = "не указан login")
    @NotBlank(message = "не указан login")
    @Pattern(regexp = "^\\S*$")
    private String login;
    private String name;
    private LocalDate birthday;
}
