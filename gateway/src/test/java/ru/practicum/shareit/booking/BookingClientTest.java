package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private BookingClient bookingClient;
    private final String serverUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        // Явно указываем тип Supplier для requestFactory
        when(restTemplateBuilder.requestFactory(any(Supplier.class)))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.uriTemplateHandler(any()))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.build())
                .thenReturn(restTemplate);

        bookingClient = new BookingClient(serverUrl, restTemplateBuilder);
    }

    @Test
    @DisplayName("Создать бронирование - корректные параметры запроса")
    void bookItem_shouldCallPostWithCorrectParameters() {
        // 1. Подготовка
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(
                eq(""),  // точное совпадение URL
                eq(HttpMethod.POST),  // точный метод
                argThat(entity -> {  // гибкая проверка entity
                    return entity.getHeaders().getFirst("X-Sharer-User-Id").equals("1") &&
                            entity.getBody() instanceof BookItemRequestDto;
                }),
                eq(Object.class)  // точный тип ответа
        )).thenReturn(expectedResponse);

        // 2. Вызов
        ResponseEntity<Object> response = bookingClient.bookItem(1L, new BookItemRequestDto());

        // 3. Проверка
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Получить бронирование - корректные параметры запроса")
    void getBookings_shouldCallGetWithCorrectParameters() {
        // Given
        long userId = 1L;
        BookingState state = BookingState.ALL;
        int from = 0;
        int size = 10;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("?state={state}&from={from}&size={size}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class),
                eq(Map.of("state", "ALL", "from", 0, "size", 10))
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = bookingClient.getBookings(userId, state, from, size);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("?state={state}&from={from}&size={size}"),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class),
                eq(Map.of("state", "ALL", "from", 0, "size", 10))
        );
    }

    @Test
    @DisplayName("Создать бронирование - корректный URL")
    void getBooking_shouldCallGetWithCorrectPath() {
        // Given
        long userId = 1L;
        long bookingId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/" + bookingId),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = bookingClient.getBooking(userId, bookingId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/" + bookingId),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Подтвердить бронирование - корректные параметры запроса")
    void approveBookingById_shouldCallPatchWithCorrectParameters() {
        // Given
        long userId = 1L;
        long bookingId = 1L;
        boolean approved = true;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/{bookingId}?approved={status}"),
                eq(HttpMethod.PATCH),
                any(HttpEntity.class),
                eq(Object.class),
                eq(Map.of("bookingId", bookingId, "status", approved))
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = bookingClient.approveBookingById(userId, bookingId, approved);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/{bookingId}?approved={status}"),
                eq(HttpMethod.PATCH),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class),
                eq(Map.of("bookingId", bookingId, "status", approved))
        );
    }


}