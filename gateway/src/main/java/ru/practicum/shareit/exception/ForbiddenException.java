package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final String reason;

    public ForbiddenException(String reason) {
        super("Запрещено");
        this.reason = reason;
    }
}
