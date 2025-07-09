package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.dto.ItemRequestDto;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id; // уникальный идентификатор вещи;
    @NotBlank(message = "Название вещи должно быть указано")
    private String name; // краткое название;
    @NotBlank(message = "Описание вещи должно быть указано")
    private String description; // развёрнутое описание;
    @NotNull(message = "Доступность вещи должна быть указана")
    private Boolean available; // статус о том, доступна или нет вещь для аренды;
    private Long ownerId; // владелец вещи;
    private Long requestId; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
    private ItemRequestDto request;
}
