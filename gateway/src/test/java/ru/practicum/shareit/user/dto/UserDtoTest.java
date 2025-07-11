package ru.practicum.shareit.user.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Создать DTO пользователя с валидными данными")
    void createUserDto_withValidData_shouldCreateObject() {
        UserDto user = new UserDto(1L, "User Name", "user@example.com");

        assertAll(
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals("User Name", user.getName()),
                () -> assertEquals("user@example.com", user.getEmail())
        );
    }

    @Test
    @DisplayName("Проверить валидацию при пустом email")
    void validateUserDto_withEmptyEmail_shouldFailValidation() {
        UserDto user = new UserDto(1L, "User Name", "");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals("Имейл должен быть указан", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Проверить валидацию при некорректном email")
    void validateUserDto_withInvalidEmail_shouldFailValidation() {
        UserDto user = new UserDto(1L, "User Name", "invalid-email");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals("Некорректный имейл", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Проверить работу конструктора по умолчанию")
    void createUserDto_withDefaultConstructor_shouldCreateEmptyObject() {
        UserDto user = new UserDto();

        assertAll(
                () -> assertNull(user.getId()),
                () -> assertNull(user.getName()),
                () -> assertNull(user.getEmail())
        );
    }

    @Test
    @DisplayName("Создать DTO пользователя с минимальными данными")
    void createUserDto_withMinimalData_shouldCreateObject() {
        UserDto user = new UserDto(null, null, "user@example.com");

        assertAll(
                () -> assertNull(user.getId()),
                () -> assertNull(user.getName()),
                () -> assertEquals("user@example.com", user.getEmail())
        );
    }
}