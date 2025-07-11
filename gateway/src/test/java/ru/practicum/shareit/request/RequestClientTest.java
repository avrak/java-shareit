package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private RequestClient requestClient;
    private final String serverUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.requestFactory(any(Supplier.class)))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.uriTemplateHandler(any()))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.build())
                .thenReturn(restTemplate);

        requestClient = new RequestClient(serverUrl, restTemplateBuilder);
    }

    @Test
    @DisplayName("Выполнить добавление нового запроса на вещь")
    void addItemRequest_shouldCallPostWithCorrectParameters() {
        // Given
        long userId = 1L;
        ItemRequestDto requestDto = new ItemRequestDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq(""),
                eq(HttpMethod.POST),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == requestDto
                ),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = requestClient.addItemRequest(userId, requestDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq(""),
                eq(HttpMethod.POST),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == requestDto
                ),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Получить список собственных запросов на вещи")
    void getMyItemRequests_shouldCallGetWithCorrectParameters() {
        // Given
        long userId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq(""),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = requestClient.getMyItemRequests(userId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq(""),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Получить список всех запросов на вещи")
    void getAllItemRequests_shouldCallGetWithoutParameters() {
        // Given
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq(""),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = requestClient.getAllItemRequests();

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq(""),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Получить данные запроса на вещь по идентификатору")
    void getItemRequestById_shouldCallGetWithCorrectPath() {
        // Given
        long userId = 1L;
        long requestId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/{requestId}"),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class),
                eq(Map.of("requestId", requestId))
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = requestClient.getItemRequestById(userId, requestId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/{requestId}"),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class),
                eq(Map.of("requestId", requestId))
        );
    }
}