package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemWideDtoTest {

    private final LocalDateTime testTime = LocalDateTime.now();
    private final BookingDto lastBooking = new BookingDto(1L, testTime.minusDays(2), testTime.minusDays(1),
            1L, 2L, null, null, null);
    private final BookingDto nextBooking = new BookingDto(2L, testTime.plusDays(1), testTime.plusDays(2),
            1L, 3L, null, null, null);
    private final CommentDto comment = new CommentDto(1L, 1L, 1L, testTime, "Автор", "Текст");

    @Test
    @DisplayName("Создать расширенное DTO вещи с полными данными")
    void createItemWideDto_withFullData_shouldCreateObject() {
        ItemWideDto item = new ItemWideDto(
                1L, "Вещь", "Описание", true, 1L, null,
                lastBooking, nextBooking, List.of(comment));

        assertAll(
                () -> assertEquals(1L, item.getId()),
                () -> assertEquals("Вещь", item.getName()),
                () -> assertEquals("Описание", item.getDescription()),
                () -> assertTrue(item.getAvailable()),
                () -> assertEquals(1L, item.getOwner()),
                () -> assertNull(item.getRequest()),
                () -> assertEquals(lastBooking, item.getLastBooking()),
                () -> assertEquals(nextBooking, item.getNextBooking()),
                () -> assertEquals(1, item.getComments().size()),
                () -> assertEquals(comment, item.getComments().iterator().next())
        );
    }

    @Test
    @DisplayName("Создать расширенное DTO вещи с минимальными данными")
    void createItemWideDto_withMinimalData_shouldCreateObject() {
        ItemWideDto item = new ItemWideDto(
                1L, "Вещь", "Описание", true, 1L, null,
                null, null, null);

        assertAll(
                () -> assertEquals(1L, item.getId()),
                () -> assertEquals("Вещь", item.getName()),
                () -> assertEquals("Описание", item.getDescription()),
                () -> assertTrue(item.getAvailable()),
                () -> assertEquals(1L, item.getOwner()),
                () -> assertNull(item.getRequest()),
                () -> assertNull(item.getLastBooking()),
                () -> assertNull(item.getNextBooking()),
                () -> assertNull(item.getComments())
        );
    }

    @Test
    @DisplayName("Проверить работу конструктора по умолчанию")
    void createItemWideDto_withDefaultConstructor_shouldCreateEmptyObject() {
        ItemWideDto item = new ItemWideDto();

        assertAll(
                () -> assertNull(item.getId()),
                () -> assertNull(item.getName()),
                () -> assertNull(item.getDescription()),
                () -> assertNull(item.getAvailable()),
                () -> assertNull(item.getOwner()),
                () -> assertNull(item.getRequest()),
                () -> assertNull(item.getLastBooking()),
                () -> assertNull(item.getNextBooking()),
                () -> assertNull(item.getComments())
        );
    }
}