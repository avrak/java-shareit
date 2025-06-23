package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.BookingClient;
import ru.practicum.shareit.booking.BookingClientController;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingClientControllerTest {

    @Mock
    private BookingClient bookingClient;

    @InjectMocks
    private BookingClientController bookingController;

    private final long userId = 1L;
    private final long bookingId = 1L;
    private BookItemRequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = new BookItemRequestDto(
            1L,
            LocalDateTime.of(2025, 06, 17, 11, 0),
            LocalDateTime.of(2025, 06, 17, 12, 0)
        );
    }

    @Test
    void getBookings_ValidRequest_ReturnsOk() {
        // Arrange
        String stateParam = "ALL";
        int from = 0;
        int size = 10;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(bookingClient.getBookings(eq(userId), eq(BookingState.ALL), eq(from), eq(size)))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> response = bookingController.getBookings(userId, stateParam, from, size);

        // Assert
        assertEquals(expectedResponse, response);
        verify(bookingClient).getBookings(userId, BookingState.ALL, from, size);
    }

    @Test
    void getBookings_InvalidState_ThrowsIllegalArgumentException() {
        // Arrange
        String invalidState = "INVALID_STATE";
        int from = 0;
        int size = 10;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> bookingController.getBookings(userId, invalidState, from, size));
        verify(bookingClient, never()).getBookings(any(), any(), any(), any());
    }

    @Test
    void getBookings_InvalidPagination_ThrowsConstraintViolationException() {
        // Arrange
        String stateParam = "ALL";
        int invalidFrom = -1;
        int invalidSize = 0;

        // Act & Assert
        assertThrows(jakarta.validation.ConstraintViolationException.class,
                () -> bookingController.getBookings(userId, stateParam, invalidFrom, 10));
        assertThrows(jakarta.validation.ConstraintViolationException.class,
                () -> bookingController.getBookings(userId, stateParam, 0, invalidSize));
        verify(bookingClient, never()).getBookings(any(), any(), any(), any());
    }

    @Test
    void bookItem_ValidRequest_ReturnsCreated() {
        // Arrange
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(bookingClient.bookItem(eq(userId), any(BookItemRequestDto.class)))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> response = bookingController.bookItem(userId, requestDto);

        // Assert
        assertEquals(expectedResponse, response);
        verify(bookingClient).bookItem(userId, requestDto);
    }

    @Test
    void bookItem_InvalidRequest_ThrowsConstraintViolationException() {
        // Arrange
        BookItemRequestDto invalidRequest = new BookItemRequestDto(); // missing required fields

        // Act & Assert
        assertThrows(jakarta.validation.ConstraintViolationException.class,
                () -> bookingController.bookItem(userId, invalidRequest));
        verify(bookingClient, never()).bookItem(any(), any());
    }

    @Test
    void getBooking_ValidRequest_ReturnsOk() {
        // Arrange
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(bookingClient.getBooking(eq(userId), eq(bookingId)))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> response = bookingController.getBooking(userId, bookingId);

        // Assert
        assertEquals(expectedResponse, response);
        verify(bookingClient).getBooking(userId, bookingId);
    }

    @Test
    void getBooking_InvalidBookingId_ThrowsConstraintViolationException() {
        // Arrange
        long invalidBookingId = -1L;

        // Act & Assert
        assertThrows(jakarta.validation.ConstraintViolationException.class,
                () -> bookingController.getBooking(userId, invalidBookingId));
        verify(bookingClient, never()).getBooking(any(), any());
    }
}
