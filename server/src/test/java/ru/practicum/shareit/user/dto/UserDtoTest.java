package ru.practicum.shareit.user.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Проверка DTO пользователя с корректными данными")
    void userDto_testValid() {
        String name = "userDto_testValid";
        String email = name + "@example.com";

        UserDto userDto = new UserDto(1L, name, email);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Проверка DTO пользователя с пустым email")
    void userDto_testEmptyEmail() {
        String name = "userDto_testEmptyEmail";
        String email = null;

        UserDto userDto = new UserDto(1L, name, email);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Проверка DTO пользователя с некорректным email")
    void userDto_testInvalidEmail() {
        String name = "userDto_testInvalidEmail";
        String email = name;

        UserDto userDto = new UserDto(1L, name, email);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Проверка геттеров и сеттеров DTO пользователя")
    void userDto_testGettersSetters() {
        String name = "userDto_testGettersSetters";
        String email = name + "@example.com";

        UserDto userDto = new UserDto();

        userDto.setId(1L);
        userDto.setName(name);
        userDto.setEmail(email);

        assertEquals(1L, userDto.getId());
        assertEquals(name, userDto.getName());
        assertEquals(email, userDto.getEmail());
    }
}
