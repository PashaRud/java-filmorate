package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Genre {
    private Integer id;
//    @JsonProperty("name")
    private String Name;
//
//    public Genre(int id, String genreName) {
//        this.id = id;
//        this.genreName = genreName;
//    }
//
//    public Genre(int id) {
//        this.id = id;
//        this.genreName = "";
//    }
}
