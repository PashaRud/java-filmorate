package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friends {
    private Integer userId;
    private Integer friendId;
    private boolean status;

    public Friends(Integer userId, Integer friendId, boolean status) {
        this.friendId = friendId;
        this.userId = userId;
        this.status = status;
    }
}