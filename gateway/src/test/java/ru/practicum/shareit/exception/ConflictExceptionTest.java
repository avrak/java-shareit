package ru.practicum.shareit.exception;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.ConflictException;

import static org.junit.jupiter.api.Assertions.*;

class ConflictExceptionTest {

    @Test
    @DisplayName("Создание исключения с причиной конфликта")
    void shouldCreateExceptionWithReason() {
        String testReason = "Пользователь уже существует";
        ConflictException exception = new ConflictException(testReason);

        // Проверяем сообщение и причину
        assertEquals("Конфликт данных", exception.getMessage());
        assertEquals(testReason, exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с пустой причиной")
    void shouldCreateExceptionWithEmptyReason() {
        ConflictException exception = new ConflictException("");

        assertEquals("Конфликт данных", exception.getMessage());
        assertEquals("", exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с null причиной")
    void shouldCreateExceptionWithNullReason() {
        ConflictException exception = new ConflictException(null);

        assertEquals("Конфликт данных", exception.getMessage());
        assertNull(exception.getReason());
    }

    @Test
    @DisplayName("Проверка наследования от RuntimeException")
    void shouldBeRuntimeExceptionSubclass() {
        ConflictException exception = new ConflictException("test");
        assertInstanceOf(RuntimeException.class, exception);
    }
}
