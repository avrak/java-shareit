package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDto {
    private long id; //уникальный идентификатор бронирования;
    private long start; // дата и время начала бронирования;
    private long end; // дата и время конца бронирования;
    private Item item; // вещь, которую пользователь бронирует;
    private User booker; // пользователь, который осуществляет бронирование;
    private Statuses status; // статус бронирования
}
