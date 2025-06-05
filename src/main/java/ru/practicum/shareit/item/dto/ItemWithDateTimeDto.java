package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
public class ItemWithDateTimeDto {
    private long id; // уникальный идентификатор вещи;
    private String name; // краткое название;
    private String description; // развёрнутое описание;
    private Boolean available; // статус о том, доступна или нет вещь для аренды;
    private Long owner; // владелец вещи;
    private Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
    private LocalDateTime lastBookingStart;
    private LocalDateTime lastBookingEnd;
    private LocalDateTime nextBookingStart;
    private LocalDateTime nextBookingEnd;
}
