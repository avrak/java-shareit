package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    private Long id; // уникальный идентификатор запроса;
    @NotBlank
    private String description; // текст запроса, содержащий описание требуемой вещи;
    private UserDto requestor; // пользователь, создавший запрос;
    private LocalDateTime created; // дата и время создания запроса.
    private Collection<ItemShortDto> items;
}
