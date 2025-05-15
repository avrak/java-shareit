package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Getter
@Setter
@Validated
public class Item {
    private long id; // уникальный идентификатор вещи;
    @NotBlank(message = "Название вещи должно быть указано")
    String name; // краткое название;
    @NotBlank(message = "Описание вещи должно быть указано")
    String description; // развёрнутое описание;
    @NotNull(message = "Доступность вещи должна быть указана")
    Boolean available; // статус о том, доступна или нет вещь для аренды;
    Long owner; // владелец вещи;
    Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
}
