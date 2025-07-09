package ru.practicum.shareit.item.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemTest {
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
    @DisplayName("Создать вещь с корректными данными")
    void createItem_withCorrectData() {
        Item item = new Item(
                1L,
                "ItemTest",
                "ItemTest description",
                true,
                1L,
                1L,
                new ItemRequest(),
                new ArrayList<>()
        );

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Создать вещь с некорректными данными")
    void createItem_withIncorrectData() {
        Item item = new Item(
                1L,
                null,
                null,
                null,
                1L,
                1L,
                new ItemRequest(),
                new ArrayList<>()
        );

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("available")));

    }

}
