package ru.practicum.shareit.exception.model;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String reason;

    public NotFoundException(String reason) {
        super("Ресурс не найден. " + reason);
        this.reason = reason;
    }
}
