package ru.practicum.shareit.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserClientControllerTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserClientController userController;

    private final Long userId = 1L;
    private final UserDto userDto = new UserDto();

    @Test
    @DisplayName("Получить пользователя по идентификатору")
    void getUserById_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(userClient.getUserById(userId)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.getUserById(userId);

        assertEquals(expectedResponse, response);
        verify(userClient).getUserById(userId);
    }

    @Test
    @DisplayName("Создать нового пользователя")
    void create_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(userClient.addUser(userDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.create(userDto);

        assertEquals(expectedResponse, response);
        verify(userClient).addUser(userDto);
    }

    @Test
    @DisplayName("Обновить данные пользователя")
    void update_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(userClient.updateUser(userId, userDto)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.update(userId, userDto);

        assertEquals(expectedResponse, response);
        verify(userClient).updateUser(userId, userDto);
    }

    @Test
    @DisplayName("Удалить пользователя")
    void delete_shouldCallClientMethod() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(userClient.deleteUser(userId)).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.delete(userId);

        assertEquals(expectedResponse, response);
        verify(userClient).deleteUser(userId);
    }
}