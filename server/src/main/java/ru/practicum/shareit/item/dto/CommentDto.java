package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private Long itemId;
    private Long user;
    private LocalDateTime created;
    private String authorName;
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String text;
}

