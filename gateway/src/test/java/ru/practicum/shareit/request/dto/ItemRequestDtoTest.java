package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestDtoTest {

    private final LocalDateTime testTime = LocalDateTime.now();
    private final UserDto testRequestor = new UserDto(1L, "User", "user@example.com");
    private final ItemDto testItem = new ItemDto();

    @Test
    @DisplayName("Создать DTO запроса с полными данными")
    void createItemRequestDto_withFullData_shouldCreateObject() {
        ItemRequestDto request = new ItemRequestDto(
                1L, "Описание запроса", testRequestor, testTime, List.of(testItem));

        assertAll(
                () -> assertEquals(1L, request.getId()),
                () -> assertEquals("Описание запроса", request.getDescription()),
                () -> assertEquals(testRequestor, request.getRequestor()),
                () -> assertEquals(testTime, request.getCreated()),
                () -> assertEquals(1, request.getItems().size()),
                () -> assertEquals(testItem, request.getItems().iterator().next())
        );
    }

    @Test
    @DisplayName("Создать DTO запроса с минимальными данными")
    void createItemRequestDto_withMinimalData_shouldCreateObject() {
        ItemRequestDto request = new ItemRequestDto(
                1L, "Описание запроса", null, null, null);

        assertAll(
                () -> assertEquals(1L, request.getId()),
                () -> assertEquals("Описание запроса", request.getDescription()),
                () -> assertNull(request.getRequestor()),
                () -> assertNull(request.getCreated()),
                () -> assertNull(request.getItems())
        );
    }

    @Test
    @DisplayName("Проверить работу конструктора по умолчанию")
    void createItemRequestDto_withDefaultConstructor_shouldCreateEmptyObject() {
        ItemRequestDto request = new ItemRequestDto();

        assertAll(
                () -> assertNull(request.getId()),
                () -> assertNull(request.getDescription()),
                () -> assertNull(request.getRequestor()),
                () -> assertNull(request.getCreated()),
                () -> assertNull(request.getItems())
        );
    }

    @Test
    @DisplayName("Проверить создание DTO с коллекцией items")
    void createItemRequestDto_withItemsCollection_shouldContainItems() {
        List<ItemDto> items = List.of(testItem, new ItemDto());
        ItemRequestDto request = new ItemRequestDto(
                1L, "Описание запроса", testRequestor, testTime, items);

        assertEquals(2, request.getItems().size());
    }
}