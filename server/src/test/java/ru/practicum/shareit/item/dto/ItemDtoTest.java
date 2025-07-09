package ru.practicum.shareit.item.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private final String name = "ItemRequestDtoTest";
    private final String email = name + "@example.com";

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
    @DisplayName("Создать DTO вещи с корректными данными")
    void createItemDto_withCorrectData() {
        ItemDto itemDto = new ItemDto(
                1L,
                "ItemDtoTest",
                "ItemDtoTest description",
                true,
                1L,
                1L,
                new ItemRequestDto()
        );

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Создать DTO вещи с некорректными данными")
    void createItemDto_withIncorrectData() {
        ItemDto itemDto = new ItemDto(
                1L,
                null,
                null,
                null,
                1L,
                1L,
                new ItemRequestDto()
        );

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("available")));
    }
}
