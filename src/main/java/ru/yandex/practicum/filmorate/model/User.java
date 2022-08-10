package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class User {

//    private Map<User, Boolean> friendsStatus;
    private Integer id;
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
    private Set<Integer> friendship = new HashSet<>();

}