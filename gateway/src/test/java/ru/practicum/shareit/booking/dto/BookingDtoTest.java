package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookingDtoTest {

    private final Validator validator;

    public BookingDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateBookingDtoWithAllFields() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        ItemDto itemDto = new ItemDto();
        UserDto userDto = new UserDto();

        // Act
        BookingDto bookingDto = new BookingDto(
                1L, start, end, 10L, 20L, itemDto, userDto, BookingState.WAITING
        );

        // Assert
        assertEquals(1L, bookingDto.getId());
        assertEquals(start, bookingDto.getStart());
        assertEquals(end, bookingDto.getEnd());
        assertEquals(10L, bookingDto.getItemId());
        assertEquals(20L, bookingDto.getBookerId());
        assertEquals(itemDto, bookingDto.getItem());
        assertEquals(userDto, bookingDto.getBooker());
        assertEquals(BookingState.WAITING, bookingDto.getStatus());
    }

    @Test
    void shouldValidateNotNullConstraints() {
        // Arrange
        BookingDto bookingDto = new BookingDto(
                null, null, null, null, null, null, null, null
        );

        // Act
        Set<ConstraintViolation<BookingDto>> violations = validator.validate(bookingDto);

        // Assert
        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Время начала бронирования должно быть указано")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Время окончания бронирования должно быть указано")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Вещь для бронирования должна быть указана")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Пользователь, бронирующий вещь, должен быть указан")));
    }

    @Test
    void shouldValidateStartBeforeEnd() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        ItemDto itemDto = new ItemDto();
        UserDto userDto = new UserDto();

        BookingDto bookingDto = new BookingDto(
                1L, start, end, 1L, 1L, itemDto, userDto, BookingState.WAITING
        );

        // Act
        Set<ConstraintViolation<BookingDto>> violations = validator.validate(bookingDto);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Дата окончания бронирования должна быть после даты начала"));
    }

    @Test
    void shouldValidateStartNotInPast() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        ItemDto itemDto = new ItemDto();
        UserDto userDto = new UserDto();

        BookingDto bookingDto = new BookingDto(
                1L, start, end, 1L, 1L, itemDto, userDto, BookingState.WAITING
        );

        // Act
        Set<ConstraintViolation<BookingDto>> violations = validator.validate(bookingDto);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Дата начала бронирования не может быть в прошлом"));
    }

    @Test
    void shouldHaveCorrectLombokAnnotations() {
        // Arrange
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        ItemDto itemDto = new ItemDto();
        UserDto userDto = new UserDto();

        // Act & Assert
        assertDoesNotThrow(() -> {
            new BookingDto(1L, start, end, 1L, 1L, itemDto, userDto, BookingState.APPROVED);
        });
    }
}