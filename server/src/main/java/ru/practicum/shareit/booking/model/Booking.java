package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //уникальный идентификатор бронирования;

    @Column(name = "started_at", nullable = false)
    @NotNull(message = "Время начала бронирования должно быть указано")
    private LocalDateTime start; // дата и время начала бронирования;

    @Column(name = "ended_at", nullable = false)
    @NotNull(message = "Время окончания бронирования должно быть указано")
    private LocalDateTime end; // дата и время конца бронирования;

    @Column(name = "item_id", nullable = false)
    @NotNull(message = "Вещь для бронирования должна быть указана")
    private Long itemId; // вещь, которую пользователь бронирует;

    @Column(name = "booker_id", nullable = false)
    @NotNull(message = "Пользователь, бронирующий вещь, должен быть указан")
    private Long bookerId; // пользователь, который осуществляет бронирование;

    @Column(name = "status")
    private String status; // статус бронирования

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booker_id", insertable = false, updatable = false)
    private User booker;



}
