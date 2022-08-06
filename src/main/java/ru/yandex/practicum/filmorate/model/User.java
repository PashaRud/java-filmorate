package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Map<User, Boolean> friendsStatus;
    private Integer id;
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
    private final Set<Integer> friends = new HashSet<>();

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public void removeFriend(Integer id) {
        friends.remove(id);
    }

    public List<Integer> getFiends() {
        return new ArrayList<>(friends);
    }

    public boolean containsFriend(Integer id){
        return friends.contains(id);
    }

}