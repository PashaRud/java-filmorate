package ru.yandex.practicum.filmorate.exception;

public class ErrorResponse {
    private String error;
    private String description;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
