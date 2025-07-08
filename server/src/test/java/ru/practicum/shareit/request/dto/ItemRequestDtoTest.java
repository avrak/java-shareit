package ru.practicum.shareit.request.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemRequestDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private final String name = "ItemRequestDtoTest";
    private final String email = name + "@example.com";
    UserDto userDto = new UserDto(1L, name, email);
    LocalDateTime createdAt = LocalDateTime.now();

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
    @DisplayName("Создать DTO запроса с корректными параметрами")
    void itemRequestDto_valid() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(
                1L,
                "itemRequestDto_valid",
                userDto,
                createdAt,
                new ArrayList<ItemShortDto>()
        );

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequestDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Создать DTO запроса с некорректными параметрами")
    void itemRequestDto_nonValid() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(
                1L,
                "",
                userDto,
                createdAt,
                new ArrayList<ItemShortDto>()
        );

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequestDto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }
}
