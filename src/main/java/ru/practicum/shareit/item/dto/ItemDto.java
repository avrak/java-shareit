package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Getter
@Setter
@Validated
public class ItemDto {
    private long id; // уникальный идентификатор вещи;
    @NotBlank(message = "Название вещи должно быть указано")
    private String name; // краткое название;
    @NotBlank(message = "Описание вещи должно быть указано")
    private String description; // развёрнутое описание;
    @NotNull(message = "Доступность вещи должна быть указана")
    private Boolean available; // статус о том, доступна или нет вещь для аренды;
    private Long owner; // владелец вещи;
    private Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
}
