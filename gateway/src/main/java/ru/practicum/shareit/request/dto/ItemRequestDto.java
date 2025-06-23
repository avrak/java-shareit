package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id; // уникальный идентификатор запроса;
    private String description; // текст запроса, содержащий описание требуемой вещи;
    private UserDto requestor; // пользователь, создавший запрос;
    private LocalDateTime created; // дата и время создания запроса.
    private Collection<ItemDto> items;
}
