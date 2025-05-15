package ru.practicum.shareit.request.model;

import ru.practicum.shareit.user.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Getter
@Setter
public class ItemRequest {
    long id; // уникальный идентификатор запроса;
    String description; // текст запроса, содержащий описание требуемой вещи;
    User requestor; // пользователь, создавший запрос;
    long created; // дата и время создания запроса.
}
