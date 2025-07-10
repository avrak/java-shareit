package ru.practicum.shareit.booking.model;

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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingTest {
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
    @DisplayName("Создать бронирование с корректными данными")
    void createBooking_withCorrectData() {
        Booking booking = new Booking(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L,
                1L,
                Statuses.APPROVED.name(),
                new Item(),
                new User()
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Создать бронирование с некорректными данными")
    void createBooking_withIncorrectData() {
        Booking booking = new Booking(
                1L,
                null,
                null,
                null,
                null,
                Statuses.APPROVED.name(),
                new Item(),
                new User()
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
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
