package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingObjDto;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

import java.util.Collection;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping
    public BookingObjDto saveBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody BookingDto bookingDto
    ) {
        log.info("Создать бронирование");

        BookingObjDto bookingObj = bookingService.saveBooking(userId, bookingDto);
        try {
            return bookingObj;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{bookingId}")
    public BookingObjDto approveBookingById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable("bookingId") Long bookingId,
            @RequestParam(name = "approved", required = true) Boolean status
    ) {
        log.info("Изменить статус бронирования {} на {}", bookingId, status);
        return bookingService.approveBookingById(userId, bookingId, status);
    }

    @GetMapping("/{bookingId}")
    public BookingObjDto getBookingById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable("bookingId") Long bookingId
    ) {
        log.info("Получить бронирование по id {} пользователем {}", bookingId, userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingObjDto> getBookingListByBookerId(
            @RequestHeader("X-Sharer-User-Id") Long bookerId
    ) {
        log.info("Получить бронирования пользователя {}", bookerId);
        return bookingService.getBookingListByBookerId(bookerId);
    }

    @GetMapping("/owner")
    public Collection<BookingObjDto> getBookingListByOwnerIdAndStatus(
            @RequestHeader("X-Sharer-User-Id") Long bookerId,
            @PathVariable("state") String state
    ) {
        log.info("Получить бронирования для всех вещей пользователя {} со статусом {}", bookerId, state);
        return bookingService.getBookingListByOwnerIdAndStatus(bookerId, state);
    }

}
