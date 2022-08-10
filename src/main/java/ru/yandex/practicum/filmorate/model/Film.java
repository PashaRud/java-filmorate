package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Film {
    private  Set<Integer> likes = new HashSet<>();
    private Set<Genre> genres;
    private Integer id;
//    @NotNull(message = "Название должно быть не пустым")
//    @NotBlank(message = "Название должно быть не пустым")
    private String name;
//    @NotNull(message = "Описание должно быть не пустым")
//    @NotBlank(message = "Описание должно быть не пустым")
//    @Length(max = 200, message = "Длина описания должна быть меньше 200 символов")
    private String description;
//    @NotNull(message = "Укажите дату выхода фильма")
//    @Past(message = "Фильм еще не вышел")
    private LocalDate releaseDate;
//    @Min(1)
    private Integer duration;

//    @NotNull(message = "Укажите возрастной рейтинг фильма")
    private Mpa mpa;


    //Добавить инициализацию полей MPA и GENRE
    public Film(int id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
//        this.likes = new HashSet<>();
        this.genres = new HashSet<>();
    }

    public void addLike(Integer userId) {
        likes.add(userId);
    }

    public void removeLike(Integer userId) {
        likes.remove(userId);
    }

    public int getLikesCount() {
        return likes.size();
    }

    public Set<Integer> getLikes() {
        return new HashSet<>(likes);
    }
}