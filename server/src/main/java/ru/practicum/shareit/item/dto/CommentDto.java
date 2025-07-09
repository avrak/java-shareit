package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotNull(message = "id вещи должна быть указана")
    private Long itemId;
    private Long user;
    private LocalDateTime created;
    private String authorName;
    @NotBlank(message = "Текст комментария не должен быть пустым")
    private String text;
}

