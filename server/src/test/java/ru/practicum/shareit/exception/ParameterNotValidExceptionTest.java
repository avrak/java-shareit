package ru.practicum.shareit.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.model.ParameterNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParameterNotValidExceptionTest {
    @Test
    @DisplayName("Создание исключения с причиной")
    void shouldCreateExceptionWithReason() {
        String testReason = "Ошибка базы данных";
        ParameterNotValidException exception = new ParameterNotValidException(testReason);

        assertEquals("Ошибка ввода " + testReason, exception.getMessage());
        assertEquals(testReason, exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с пустой причиной")
    void shouldCreateExceptionWithEmptyReason() {
        ParameterNotValidException exception = new ParameterNotValidException("");

        assertEquals("Ошибка ввода ", exception.getMessage());
        assertEquals("", exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с null причиной")
    void shouldCreateExceptionWithNullReason() {
        ParameterNotValidException exception = new ParameterNotValidException(null);

        assertEquals("Ошибка ввода null", exception.getMessage());
        assertNull(exception.getReason());
    }

    @Test
    @DisplayName("Проверка корректности работы Lombok @Getter")
    void shouldHaveLombokGetter() {
        String testReason = "Test reason";
        ParameterNotValidException exception = new ParameterNotValidException(testReason);

        assertEquals(testReason, exception.getReason());
    }
}
