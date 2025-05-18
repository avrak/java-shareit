package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemRequestDto {
    private long id; // уникальный идентификатор запроса;
    private String description; // текст запроса, содержащий описание требуемой вещи;
    private User requestor; // пользователь, создавший запрос;
    private long created; // дата и время создания запроса.
}
