package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
public class BookingObjDto {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    ItemDto item;
    UserDto booker;
    String status;
}
