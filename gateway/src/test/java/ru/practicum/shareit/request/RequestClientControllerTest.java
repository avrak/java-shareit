package ru.practicum.shareit.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestClientControllerTest {

    @Mock
    private RequestClient requestClient;

    @InjectMocks
    private RequestClientController requestController;

    private final Long userId = 1L;
    private final Long requestId = 1L;
    private final ItemRequestDto itemRequestDto = new ItemRequestDto();

    @Test
    @DisplayName("Создать новый запрос на вещь")
    void addItemRequest_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(requestClient.addItemRequest(userId, itemRequestDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.addItemRequest(userId, itemRequestDto);

        assertEquals(expectedResponse, response);
        verify(requestClient).addItemRequest(userId, itemRequestDto);
    }

    @Test
    @DisplayName("Получить запросы текущего пользователя")
    void getMyItemRequests_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(requestClient.getMyItemRequests(userId)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.getMyItemRequests(userId);

        assertEquals(expectedResponse, response);
        verify(requestClient).getMyItemRequests(userId);
    }

    @Test
    @DisplayName("Получить все запросы")
    void getAllItemRequests_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(requestClient.getAllItemRequests()).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.getAllItemRequests(userId);

        assertEquals(expectedResponse, response);
        verify(requestClient).getAllItemRequests();
    }

    @Test
    @DisplayName("Получить запрос по идентификатору")
    void getItemRequestById_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(requestClient.getItemRequestById(userId, requestId)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.getItemRequestById(userId, requestId);

        assertEquals(expectedResponse, response);
        verify(requestClient).getItemRequestById(userId, requestId);
    }
}