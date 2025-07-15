package ru.practicum.shareit.booking.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookItemRequestDtoTest {

    private static Validator validator;
    private static LocalDateTime validStart;
    private static LocalDateTime validEnd;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        validStart = LocalDateTime.now().plusDays(1);
        validEnd = LocalDateTime.now().plusDays(2);
    }

    @Test
    void shouldCreateValidBookItemRequestDto() {
        // Arrange & Act
        BookItemRequestDto dto = new BookItemRequestDto(1L, validStart, validEnd);

        // Assert
        assertEquals(1L, dto.getItemId());
        assertEquals(validStart, dto.getStart());
        assertEquals(validEnd, dto.getEnd());
    }

    @Test
    void shouldValidateItemIdIsPositive() {
        // Arrange
        BookItemRequestDto dto = new BookItemRequestDto(0L, validStart, validEnd);

        // Act
        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("ID вещи должен быть положительным числом"));
    }

    @Test
    void shouldValidateStartIsFutureOrPresent() {
        // Arrange
        LocalDateTime pastStart = LocalDateTime.now().minusDays(1);
        BookItemRequestDto dto = new BookItemRequestDto(1L, pastStart, validEnd);

        // Act
        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Дата начала должна быть текущей или в будущем"));
    }

    @Test
    void shouldValidateEndIsFuture() {
        // Arrange
        LocalDateTime pastEnd = LocalDateTime.now().minusDays(1);
        BookItemRequestDto dto = new BookItemRequestDto(1L, validStart, pastEnd);

        // Act
        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.toString().contains("Дата окончания должна быть будущем"));
    }

    @Test
    void shouldValidateEndAfterStart() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        BookItemRequestDto dto = new BookItemRequestDto(1L, start, end);

        // Act
        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(dto);

        // Assert
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Дата окончания бронирования должна быть после даты начала"));
    }

    @Test
    void shouldHaveCorrectLombokAnnotations() {
        // Arrange & Act
        BookItemRequestDto dto = new BookItemRequestDto();

        // Assert
        assertNotNull(dto);
        assertDoesNotThrow(() -> {
            new BookItemRequestDto(1L, validStart, validEnd);
        });
    }

    @Test
    void isEndAfterStart_shouldValidateDateOrder() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validStart = now.plusHours(1);
        LocalDateTime validEnd = now.plusHours(2);
        LocalDateTime invalidEnd = now.minusHours(1);

        // Act & Assert для корректных дат
        BookItemRequestDto validDto = new BookItemRequestDto(1L, validStart, validEnd);
        assertTrue(validDto.isEndAfterStart(), "Должно возвращать true при корректных датах");

        // Act & Assert для некорректных дат
        BookItemRequestDto invalidDto = new BookItemRequestDto(1L, validStart, invalidEnd);
        assertFalse(invalidDto.isEndAfterStart(), "Должно возвращать false при дате окончания раньше начала");

        // Act & Assert для null значений
        BookItemRequestDto nullDatesDto = new BookItemRequestDto();
        assertTrue(nullDatesDto.isEndAfterStart(), "Должно возвращать true при null датах");
    }
}