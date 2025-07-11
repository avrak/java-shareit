package ru.practicum.shareit.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemClientControllerTest {

    @Mock
    private ItemClient itemClient;

    @InjectMocks
    private ItemClientController itemController;

    private final Long userId = 1L;
    private final Long itemId = 1L;
    private final ItemDto itemDto = new ItemDto();
    private final CommentDto commentDto = new CommentDto();

    @Test
    @DisplayName("Добавить новую вещь")
    void addItem_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(itemClient.addItem(userId, itemDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemController.addItem(userId, itemDto);

        assertEquals(expectedResponse, response);
        verify(itemClient).addItem(userId, itemDto);
    }

    @Test
    @DisplayName("Обновить существующую вещь")
    void updateItem_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(itemClient.updateItem(userId, itemId, itemDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemController.updateItem(userId, itemId, itemDto);

        assertEquals(expectedResponse, response);
        verify(itemClient).updateItem(userId, itemId, itemDto);
    }

    @Test
    @DisplayName("Получить вещь по идентификатору")
    void getItemById_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(itemClient.getItemById(userId, itemId)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemController.getItemById(userId, itemId);

        assertEquals(expectedResponse, response);
        verify(itemClient).getItemById(userId, itemId);
    }

    @Test
    @DisplayName("Получить список вещей владельца")
    void getItemListByOwnerId_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(itemClient.getItemListByOwnerId(userId)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemController.getItemListByOwnerId(userId);

        assertEquals(expectedResponse, response);
        verify(itemClient).getItemListByOwnerId(userId);
    }

    @Test
    @DisplayName("Найти вещи по тексту запроса")
    void getItemListByText_shouldCallClientMethodForNonEmptyText() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        String searchText = "test";
        when(itemClient.getItemListByText(searchText)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemController.getItemListByText(searchText);

        assertEquals(expectedResponse, response);
        verify(itemClient).getItemListByText(searchText);
    }

    @Test
    @DisplayName("Вернуть пустой список при пустом тексте запроса")
    void getItemListByText_shouldReturnEmptyListForEmptyText() {
        ResponseEntity<Object> response = itemController.getItemListByText("");

        assertEquals(0, ((Iterable<?>) response.getBody()).spliterator().getExactSizeIfKnown());
        verify(itemClient, never()).getItemListByText(anyString());
    }

    @Test
    @DisplayName("Добавить комментарий к вещи")
    void addComment_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(itemClient.addComment(userId, itemId, commentDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemController.addComment(userId, itemId, commentDto);

        assertEquals(expectedResponse, response);
        verify(itemClient).addComment(userId, itemId, commentDto);
    }
}