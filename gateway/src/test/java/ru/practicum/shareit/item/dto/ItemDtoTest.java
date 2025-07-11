package ru.practicum.shareit.item.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemDtoTest {

    private static Validator validator;
    private final UserDto testOwner = new UserDto(1L, "Owner", "owner@example.com");
    private final ItemRequestDto testRequest = new ItemRequestDto();

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Создать DTO вещи с полными данными")
    void createItemDto_withFullData_shouldCreateObject() {
        ItemDto item = new ItemDto(
                1L, "Вещь", "Описание", true,
                1L, testOwner, 1L, List.of(testRequest));

        assertAll(
                () -> assertEquals(1L, item.getItemId()),
                () -> assertEquals("Вещь", item.getName()),
                () -> assertEquals("Описание", item.getDescription()),
                () -> assertTrue(item.getAvailable()),
                () -> assertEquals(1L, item.getOwnerId()),
                () -> assertEquals(testOwner, item.getOwner()),
                () -> assertEquals(1L, item.getRequestId()),
                () -> assertEquals(1, item.getRequests().size()),
                () -> assertEquals(testRequest, item.getRequests().iterator().next())
        );
    }

    @Test
    @DisplayName("Создать DTO вещи с минимальными данными")
    void createItemDto_withMinimalData_shouldCreateObject() {
        ItemDto item = new ItemDto(
                1L, "Вещь", "Описание", true,
                null, null, null, null);

        assertAll(
                () -> assertEquals(1L, item.getItemId()),
                () -> assertEquals("Вещь", item.getName()),
                () -> assertEquals("Описание", item.getDescription()),
                () -> assertTrue(item.getAvailable()),
                () -> assertNull(item.getOwnerId()),
                () -> assertNull(item.getOwner()),
                () -> assertNull(item.getRequestId()),
                () -> assertNull(item.getRequests())
        );
    }

    @Test
    @DisplayName("Проверить валидацию при пустом названии")
    void validateItemDto_withEmptyName_shouldFailValidation() {
        ItemDto item = new ItemDto(
                1L, "", "Описание", true,
                1L, testOwner, 1L, List.of(testRequest));

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertEquals("Название вещи должно быть указано", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Проверить валидацию при пустом описании")
    void validateItemDto_withEmptyDescription_shouldFailValidation() {
        ItemDto item = new ItemDto(
                1L, "Вещь", "", true,
                1L, testOwner, 1L, List.of(testRequest));

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertEquals("Описание вещи должно быть указано", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Проверить валидацию при отсутствии статуса доступности")
    void validateItemDto_withNullAvailable_shouldFailValidation() {
        ItemDto item = new ItemDto(
                1L, "Вещь", "Описание", null,
                1L, testOwner, 1L, List.of(testRequest));

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertEquals("Доступность вещи должна быть указана", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Проверить работу конструктора по умолчанию")
    void createItemDto_withDefaultConstructor_shouldCreateEmptyObject() {
        ItemDto item = new ItemDto();

        assertAll(
                () -> assertEquals(0L, item.getItemId()),
                () -> assertNull(item.getName()),
                () -> assertNull(item.getDescription()),
                () -> assertNull(item.getAvailable()),
                () -> assertNull(item.getOwnerId()),
                () -> assertNull(item.getOwner()),
                () -> assertNull(item.getRequestId()),
                () -> assertNull(item.getRequests())
        );
    }
}