package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
public class BookingObjDto {
    Long id; //уникальный идентификатор бронирования;
    LocalDateTime start; // дата и время начала бронирования;
    LocalDateTime end; // дата и время конца бронирования;
    ItemDto item; // вещь, которую пользователь бронирует;
    UserDto booker; // пользователь, который осуществляет бронирование;
    String status; // статус бронирования
}
