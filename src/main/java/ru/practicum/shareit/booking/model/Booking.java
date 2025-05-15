package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.item.model.Item;
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
public class Booking {
    long id; //уникальный идентификатор бронирования;
    long start; // дата и время начала бронирования;
    long end; // дата и время конца бронирования;
    Item item; // вещь, которую пользователь бронирует;
    User booker; // пользователь, который осуществляет бронирование;
    Statuses status; // статус бронирования
}
