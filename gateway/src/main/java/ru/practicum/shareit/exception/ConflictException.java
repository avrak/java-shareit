package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final String reason;

    public ConflictException(String reason) {
        super("Конфликт данных");
        this.reason = reason;
    }
}