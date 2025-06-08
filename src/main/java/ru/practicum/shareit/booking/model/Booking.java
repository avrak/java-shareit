package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; //уникальный идентификатор бронирования;

    @NotNull(message = "Время начала бронирования должно быть указано")
    @Column(name = "started_at")
    LocalDateTime start; // дата и время начала бронирования;

    @NotNull(message = "Время окончания бронирования должно быть указано")
    @Column(name = "ended_at")
    LocalDateTime end; // дата и время конца бронирования;

    @NotNull(message = "Вещь для бронирования должна быть указана")
    @Column(name = "item_id")
    Long item; // вещь, которую пользователь бронирует;

    @NotNull(message = "Пользователь, бронирующий вещь, должен быть указан")
    Long booker; // пользователь, который осуществляет бронирование;

    String status; // статус бронирования
}
