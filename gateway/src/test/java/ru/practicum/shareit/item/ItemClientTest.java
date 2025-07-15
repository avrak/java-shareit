package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private ItemClient itemClient;
    private final String serverUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.requestFactory(any(Supplier.class)))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.uriTemplateHandler(any()))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.build())
                .thenReturn(restTemplate);

        itemClient = new ItemClient(serverUrl, restTemplateBuilder);
    }

    @Test
    @DisplayName("Выполнить добавление новой вещи")
    void addItem_shouldCallPostWithCorrectParameters() {
        // Given
        long userId = 1L;
        ItemDto itemDto = new ItemDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq(""),
                eq(HttpMethod.POST),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == itemDto
                ),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = itemClient.addItem(userId, itemDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq(""),
                eq(HttpMethod.POST),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == itemDto
                ),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Выполнить обновление данных вещи")
    void updateItem_shouldCallPatchWithCorrectParameters() {
        // Given
        long userId = 1L;
        long itemId = 1L;
        ItemDto itemDto = new ItemDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/" + itemId),
                eq(HttpMethod.PATCH),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == itemDto
                ),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = itemClient.updateItem(userId, itemId, itemDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/" + itemId),
                eq(HttpMethod.PATCH),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == itemDto
                ),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Получить данные вещи по идентификатору")
    void getItemById_shouldCallGetWithCorrectPath() {
        // Given
        long userId = 1L;
        long itemId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/" + itemId),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = itemClient.getItemById(userId, itemId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/" + itemId),
                eq(HttpMethod.GET),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId))
                ),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Получить список вещей владельца")
    void getItemListByOwnerId_shouldCallGetWithCorrectParameters() {
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
        ResponseEntity<Object> actualResponse = itemClient.getItemListByOwnerId(userId);

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
    @DisplayName("Выполнить поиск вещей по тексту")
    void getItemListByText_shouldCallGetWithCorrectParameters() {
        // Given
        String searchText = "test";
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/search?text={text}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class),
                eq(Map.of("text", searchText))
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = itemClient.getItemListByText(searchText);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/search?text={text}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class),
                eq(Map.of("text", searchText))
        );
    }

    @Test
    @DisplayName("Добавить комментарий к вещи")
    void addComment_shouldCallPostWithCorrectParameters() {
        // Given
        long userId = 1L;
        long itemId = 1L;
        CommentDto commentDto = new CommentDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/" + itemId + "/comment"),
                eq(HttpMethod.POST),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == commentDto
                ),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = itemClient.addComment(userId, itemId, commentDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/" + itemId + "/comment"),
                eq(HttpMethod.POST),
                argThat(entity ->
                        entity.getHeaders().getFirst("X-Sharer-User-Id").equals(String.valueOf(userId)) &&
                                entity.getBody() == commentDto
                ),
                eq(Object.class)
        );
    }
}