package ru.practicum.shareit.booking.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingDtoTest {
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
    @DisplayName("Создать DTO бронирования с корректными данными")
    void createBookingDto_withCorrectData() {
        BookingDto bookingDto = new BookingDto(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L,
                1L,
                new ItemDto(),
                new UserDto(),
                Statuses.APPROVED
        );

        Set<ConstraintViolation<BookingDto>> violations = validator.validate(bookingDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Создать DTO бронирования с некорректными данными")
    void createBookingDto_withIncorrectData() {
        BookingDto bookingDto = new BookingDto(
                1L,
                null,
                null,
                null,
                null,
                new ItemDto(),
                new UserDto(),
                Statuses.APPROVED
        );

        Set<ConstraintViolation<BookingDto>> violations = validator.validate(bookingDto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("start")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("end")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("itemId")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("bookerId")));
    }
}
