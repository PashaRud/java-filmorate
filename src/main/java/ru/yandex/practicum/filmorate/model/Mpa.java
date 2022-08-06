package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mpa {
    private String name;
    private Integer id;

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
