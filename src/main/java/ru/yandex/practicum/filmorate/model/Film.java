package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Film {
    private final Set<Long> likes = new HashSet<>();
    private Set<Genre> genre;
    private Integer id;
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
    private Mpa mpa;


    //Добавить инициализацию полей MPA и GENRE
    public Film(int id, String name, String description, LocalDate releaseDate, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
//        this.likes = new HashSet<>();
        this.genre = new HashSet<>();
    }

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Integer userId) {
        likes.remove(userId);
    }

    public int getLikesCount() {
        return likes.size();
    }

    public Set<Long> getLikes() {
        return new HashSet<>(likes);
    }
}