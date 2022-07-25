package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
public class User {
    private Set<Integer> friends;
    private Map<User, Boolean> friendsStatus;
    private int id;
    @NotNull(message = "не указан email")
    @NotBlank(message = "email не должен состоять из пробелов")
    @Email(message = "некорректный формат email-а")
    private String email;
    @NotNull(message = "не указан login")
    @NotBlank(message = "не указан login")
    @Pattern(regexp = "^\\S*$")
    @Pattern(regexp = "[a-zA-Z\\d]+")
    private String login;
    private String name;
    @NotNull(message = "Укажите дату рождения")
    @PastOrPresent(message = "Некорректная дата рождения.")
    private LocalDate birthday;
    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
        this.friendsStatus = new HashMap<>();
    }

}