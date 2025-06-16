package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemRequestDto {
    private Long id; // уникальный идентификатор запроса;
    private String description; // текст запроса, содержащий описание требуемой вещи;
    private UserDto requestor; // пользователь, создавший запрос;
    private LocalDateTime created; // дата и время создания запроса.
}
