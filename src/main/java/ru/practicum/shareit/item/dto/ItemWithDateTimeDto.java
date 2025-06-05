package ru.practicum.shareit.item.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

@Data
@Getter
@Setter
@ToString
public class ItemWithDateTimeDto {
    private Long id; // уникальный идентификатор вещи;
    private String name; // краткое название;
    private String description; // развёрнутое описание;
    private Boolean available; // статус о том, доступна или нет вещь для аренды;
    private Long owner; // владелец вещи;
    private Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
    @Nullable
    private BookingDto lastBooking;
    @Nullable
    private BookingDto nextBooking;
    @Nullable
    private Collection<CommentDto> comments;
}
