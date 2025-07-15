package ru.practicum.shareit.user.model;

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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
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
    @DisplayName("Проверка пользователя с корректными данными")
    void user_testValid() {
        String name = "user_testValid";
        String email = name + "@example.com";

        User user = new User(1L, name, email);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Проверка пользователя с пустым email")
    void user_testEmptyEmail() {
        String name = "user_testEmptyEmail";
        String email = null;

        User user = new User(1L, name, email);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Проверка пользователя с некорректным email")
    void user_testInvalidEmail() {
        String name = "user_testInvalidEmail";
        String email = name;

        User user = new User(1L, name, email);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Проверка геттеров и сеттеров пользователя")
    void user_testGettersSetters() {
        String name = "user_testGettersSetters";
        String email = name + "@example.com";

        User user = new User();

        user.setId(1L);
        user.setName(name);
        user.setEmail(email);

        assertEquals(1L, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }
}
