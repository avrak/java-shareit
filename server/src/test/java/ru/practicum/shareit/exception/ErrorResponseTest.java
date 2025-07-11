package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import ru.practicum.shareit.exception.model.ErrorResponse;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    @DisplayName("Создание объекта с ошибкой и описанием")
    void shouldCreateWithErrorAndDescription() {
        String testError = "Validation error";
        String testDescription = "Invalid email format";

        ErrorResponse response = new ErrorResponse(testError, testDescription);

        assertEquals(testError, response.getError());
        assertEquals(testDescription, response.getDescription());
    }

    @Test
    @DisplayName("Создание объекта с пустыми строками")
    void shouldCreateWithEmptyStrings() {
        ErrorResponse response = new ErrorResponse("", "");

        assertEquals("", response.getError());
        assertEquals("", response.getDescription());
    }

    @Test
    @DisplayName("Создание объекта с null значениями")
    void shouldCreateWithNullValues() {
        ErrorResponse response = new ErrorResponse(null, null);

        assertNull(response.getError());
        assertNull(response.getDescription());
    }

    @Test
    @DisplayName("Проверка неизменяемости полей после создания")
    void shouldHaveImmutableFields() {
        String originalError = "Original error";
        String originalDescription = "Original description";

        ErrorResponse response = new ErrorResponse(originalError, originalDescription);

        String modifiedError = response.getError().toUpperCase();
        String modifiedDescription = response.getDescription().toUpperCase();

        assertEquals(originalError, response.getError());
        assertEquals(originalDescription, response.getDescription());
    }
}