package ru.practicum.shareit.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.InternalServerException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InternalServerExceptionTest {

    @Test
    @DisplayName("Создание исключения с причиной")
    void shouldCreateExceptionWithReason() {
        String testReason = "Ошибка базы данных";
        InternalServerException exception = new InternalServerException(testReason);

        assertEquals("Внутренняя ошибка. " + testReason, exception.getMessage());
        assertEquals(testReason, exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с пустой причиной")
    void shouldCreateExceptionWithEmptyReason() {
        InternalServerException exception = new InternalServerException("");

        assertEquals("Внутренняя ошибка. ", exception.getMessage());
        assertEquals("", exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с null причиной")
    void shouldCreateExceptionWithNullReason() {
        InternalServerException exception = new InternalServerException(null);

        assertEquals("Внутренняя ошибка. null", exception.getMessage());
        assertNull(exception.getReason());
    }

    @Test
    @DisplayName("Проверка корректности работы Lombok @Getter")
    void shouldHaveLombokGetter() {
        String testReason = "Test reason";
        InternalServerException exception = new InternalServerException(testReason);

        assertEquals(testReason, exception.getReason());
    }
}
