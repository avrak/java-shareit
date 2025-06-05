package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long itemId;
    private Long user;
    private LocalDateTime created;
    private String authorName;
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String text;
}

