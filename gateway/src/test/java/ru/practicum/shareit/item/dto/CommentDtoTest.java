package ru.practicum.shareit.item.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommentDtoTest {

    private static Validator validator;
    private static final LocalDateTime testTime = LocalDateTime.now();

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Создать комментарий с валидными данными")
    void createCommentDto_withValidData_shouldCreateObject() {
        CommentDto comment = new CommentDto(1L, 2L, 3L, testTime, "Автор", "Текст комментария");

        assertEquals(1L, comment.getId());
        assertEquals(2L, comment.getItemId());
        assertEquals(3L, comment.getUser());
        assertEquals(testTime, comment.getCreated());
        assertEquals("Автор", comment.getAuthorName());
        assertEquals("Текст комментария", comment.getText());
    }

    @Test
    @DisplayName("Проверить валидацию пустого текста комментария")
    void validateCommentDto_withEmptyText_shouldFailValidation() {
        CommentDto comment = new CommentDto(1L, 2L, 3L, testTime, "Автор", "");

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
        assertEquals("Комментарий не должен быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Проверить валидацию null текста комментария")
    void validateCommentDto_withNullText_shouldFailValidation() {
        CommentDto comment = new CommentDto(1L, 2L, 3L, testTime, "Автор", null);

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
        assertEquals("Комментарий не должен быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Проверить создание комментария через конструктор по умолчанию")
    void createCommentDto_withDefaultConstructor_shouldCreateEmptyObject() {
        CommentDto comment = new CommentDto();

        assertNull(comment.getId());
        assertNull(comment.getItemId());
        assertNull(comment.getUser());
        assertNull(comment.getCreated());
        assertNull(comment.getAuthorName());
        assertNull(comment.getText());
    }

    @Test
    @DisplayName("Проверить работу Lombok аннотаций")
    void checkLombokAnnotations_shouldWorkCorrectly() {
        CommentDto comment = new CommentDto(1L, 2L, 3L, testTime, "Автор", "Текст");

        assertEquals(1L, comment.getId());
        assertEquals(2L, comment.getItemId());
        assertEquals(3L, comment.getUser());
        assertEquals(testTime, comment.getCreated());
        assertEquals("Автор", comment.getAuthorName());
        assertEquals("Текст", comment.getText());
    }
}