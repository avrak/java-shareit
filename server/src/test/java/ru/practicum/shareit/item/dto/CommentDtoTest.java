package ru.practicum.shareit.item.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Comment;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentDtoTest {
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
    @DisplayName("Создать DTO комментария с корректными данными")
    void createCommentDto_withCorrectData() {
        CommentDto commentDto = new CommentDto(
                1L,
                1L,
                1L,
                LocalDateTime.now(),
                "CommentDtoTest",
                "CommentDtoTest"
        );

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(commentDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Создать DTO комментария с некорректными данными")
    void createCommentDto_withIncorrectData() {
        CommentDto commentDto = new CommentDto(
                1L,
                null,
                1L,
                LocalDateTime.now(),
                "CommentDtoTest",
                null
        );

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(commentDto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("itemId")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("text")));
    }

}
