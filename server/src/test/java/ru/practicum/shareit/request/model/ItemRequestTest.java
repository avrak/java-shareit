package ru.practicum.shareit.request.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemRequestTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private final String name = "UserServiceTest";
    private final String email = name + "@example.com";
    private final LocalDateTime createdAt = LocalDateTime.now();

    User user = new User(1L, name, email);

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
    @DisplayName("Создать запрос с корректными параметрами")
    void itemRequest_valid() {
        ItemRequest itemRequest = new ItemRequest(1L, "ItemRequestServiceTest", user, createdAt, new ArrayList<Item>());

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(itemRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Создать запрос с некорректными параметрами")
    void itemRequest_invalid() {
        ItemRequest itemRequest = new ItemRequest(1L, null, user, createdAt, new ArrayList<Item>());

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(itemRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }
}
