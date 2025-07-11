package ru.practicum.shareit.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NotFoundExceptionTest {
    @Test
    @DisplayName("Создание исключения с причиной")
    void shouldCreateExceptionWithReason() {
        String testReason = "Ошибка базы данных";
        NotFoundException exception = new NotFoundException(testReason);

        assertEquals("Ресурс не найден. " + testReason, exception.getMessage());
        assertEquals(testReason, exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с пустой причиной")
    void shouldCreateExceptionWithEmptyReason() {
        NotFoundException exception = new NotFoundException("");

        assertEquals("Ресурс не найден. ", exception.getMessage());
        assertEquals("", exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с null причиной")
    void shouldCreateExceptionWithNullReason() {
        NotFoundException exception = new NotFoundException(null);

        assertEquals("Ресурс не найден. null", exception.getMessage());
        assertNull(exception.getReason());
    }

    @Test
    @DisplayName("Проверка корректности работы Lombok @Getter")
    void shouldHaveLombokGetter() {
        String testReason = "Test reason";
        NotFoundException exception = new NotFoundException(testReason);

        assertEquals(testReason, exception.getReason());
    }
}
