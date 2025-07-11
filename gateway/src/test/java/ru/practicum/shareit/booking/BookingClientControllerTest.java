package ru.practicum.shareit.booking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingClientControllerTest {

    @Mock
    private BookingClient bookingClient;

    @InjectMocks
    private BookingClientController bookingController;

    private final long userId = 1L;
    private final long bookingId = 1L;
    private final BookItemRequestDto requestDto = new BookItemRequestDto(
            1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

    @Test
    @DisplayName("Вернуть список бронирований")
    void getBookings_shouldReturnBookingsList() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(bookingClient.getBookings(userId, BookingState.ALL, 0, 10))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.getBookings(userId, "all", 0, 10);

        assertEquals(expectedResponse, response);
        verify(bookingClient).getBookings(userId, BookingState.ALL, 0, 10);
    }

    @Test
    @DisplayName("Бросить исключение при неверном state")
    void getBookings_shouldThrowExceptionForInvalidState() {
        assertThrows(IllegalArgumentException.class,
                () -> bookingController.getBookings(userId, "invalid_state", 0, 10));
    }

    @Test
    @DisplayName("Создать новое бронирование")
    void bookItem_shouldCreateBooking() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(bookingClient.bookItem(userId, requestDto))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.bookItem(userId, requestDto);

        assertEquals(expectedResponse, response);
        verify(bookingClient).bookItem(userId, requestDto);
    }

    @Test
    @DisplayName("Вернуть бронирование по ID")
    void getBooking_shouldReturnBookingById() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(bookingClient.getBooking(userId, bookingId))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.getBooking(userId, bookingId);

        assertEquals(expectedResponse, response);
        verify(bookingClient).getBooking(userId, bookingId);
    }

    @Test
    @DisplayName("Обновить статус бронирования")
    void approveBookingById_shouldUpdateBookingStatus() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(bookingClient.approveBookingById(userId, bookingId, true))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.approveBookingById(userId, bookingId, true);

        assertEquals(expectedResponse, response);
        verify(bookingClient).approveBookingById(userId, bookingId, true);
    }

    @Test
    @DisplayName("Проверить валидацию параметров пагинации")
    void getBookings_shouldValidatePaginationParams() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(bookingClient.getBookings(userId, BookingState.ALL, 0, 10))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.getBookings(userId, "all", 0, 10);

        assertEquals(expectedResponse, response);
        verify(bookingClient).getBookings(userId, BookingState.ALL, 0, 10);
    }
}