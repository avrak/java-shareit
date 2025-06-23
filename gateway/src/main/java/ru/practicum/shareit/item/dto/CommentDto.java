package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long itemId;
    private Long user;
    private LocalDateTime created;
    private String authorName;
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String text;
}

