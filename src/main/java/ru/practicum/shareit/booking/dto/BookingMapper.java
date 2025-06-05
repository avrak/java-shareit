package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setItemId(booking.getItem());
        bookingDto.setBooker(booking.getBooker());
        bookingDto.setStatus(Statuses.valueOf(booking.getStatus()));

        return bookingDto;
    }

    public static Booking toBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        booking.setId(bookingDto.getId());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(bookingDto.getItemId());
        booking.setBooker(bookingDto.getBooker());
        booking.setStatus(bookingDto.getStatus().name());

        return booking;
    }

    public static BookingObjDto toBookingObjDto(Booking booking, ItemDto item, UserDto booker) {
        BookingObjDto bookingObjDto = new BookingObjDto();

        bookingObjDto.setId(booking.getId());
        bookingObjDto.setStart(booking.getStart());
        bookingObjDto.setEnd(booking.getEnd());
        bookingObjDto.setItem(item);
        bookingObjDto.setBooker(booker);
        bookingObjDto.setStatus(Statuses.valueOf(booking.getStatus()).name());

        return bookingObjDto;
    }
}
