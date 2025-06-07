package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingObjDto;

import java.util.Collection;

public interface BookingService {
    public BookingObjDto saveBooking(Long userId, BookingDto bookingDto);

    public BookingObjDto approveBookingById(Long userId, Long bookingId, Boolean status);

    public BookingObjDto getBookingById(Long userId, Long bookingId);

    public Collection<BookingObjDto> getBookingListByBookerId(Long bookerId);

    public Collection<BookingObjDto> getBookingListByOwnerIdAndStatus(Long ownerId, String status);
}
