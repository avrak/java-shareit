package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id; //уникальный идентификатор бронирования;

    @NotNull(message = "Время начала бронирования должно быть указано")
    private LocalDateTime start; // дата и время начала бронирования;

    @NotNull(message = "Время окончания бронирования должно быть указано")
    private LocalDateTime end; // дата и время конца бронирования;

    @NotNull(message = "Вещь для бронирования должна быть указана")
    private Long itemId; // вещь, которую пользователь бронирует;

    @NotNull(message = "Пользователь, бронирующий вещь, должен быть указан")
    private Long bookerId;

    ItemDto item;
    UserDto booker;

    private BookingState status; // статус бронирования

    @AssertTrue(message = "Дата окончания бронирования должна быть после даты начала")
    private boolean isEndAfterStart() {
        return end == null || start == null || end.isAfter(start);
    }

    @AssertTrue(message = "Дата начала бронирования не может быть в прошлом")
    private boolean isStartInFuture() {
        return start == null || start.isAfter(LocalDateTime.now());
    }
}
