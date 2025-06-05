package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long item;
    private Long user;
    private LocalDateTime createdAt;
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String comment;
}

