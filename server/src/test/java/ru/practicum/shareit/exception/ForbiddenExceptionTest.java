package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import ru.practicum.shareit.exception.model.ForbiddenException;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenExceptionTest {

    @Test
    @DisplayName("Создание исключения с причиной")
    void shouldCreateExceptionWithReason() {
        String testReason = "Недостаточно прав";
        ForbiddenException exception = new ForbiddenException(testReason);

        assertEquals("Запрещено", exception.getMessage());
        assertEquals(testReason, exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с пустой причиной")
    void shouldCreateExceptionWithEmptyReason() {
        ForbiddenException exception = new ForbiddenException("");

        assertEquals("Запрещено", exception.getMessage());
        assertEquals("", exception.getReason());
    }

    @Test
    @DisplayName("Создание исключения с null причиной")
    void shouldCreateExceptionWithNullReason() {
        ForbiddenException exception = new ForbiddenException(null);

        assertEquals("Запрещено", exception.getMessage());
        assertNull(exception.getReason());
    }

    @Test
    @DisplayName("Проверка наличия аннотации Lombok @Getter")
    void shouldHaveLombokGetterAnnotation() {
        try {
            ForbiddenException.class.getMethod("getReason");
        } catch (NoSuchMethodException e) {
            fail("Метод getReason() должен быть создан аннотацией @Getter");
        }
    }
}
