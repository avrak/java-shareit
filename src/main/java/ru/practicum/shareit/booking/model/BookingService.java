package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

public interface BookingService {
    BookingDto saveBooking(Long userId, BookingDto bookingDto);

    BookingDto approveBookingById(Long userId, Long bookingId, Boolean status);

    BookingDto getBookingById(Long userId, Long bookingId);

    Collection<BookingDto> getBookingListByBookerId(Long bookerId);

    Collection<BookingDto> getBookingListByOwnerIdAndStatus(Long ownerId, String status);
}
