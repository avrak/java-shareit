package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private UserClient userClient;
    private final String serverUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.requestFactory(any(Supplier.class)))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.uriTemplateHandler(any()))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.build())
                .thenReturn(restTemplate);

        userClient = new UserClient(serverUrl, restTemplateBuilder);
    }

    @Test
    @DisplayName("Выполнить добавление нового пользователя")
    void addUser_shouldCallPostWithCorrectParameters() {
        // Given
        UserDto userDto = new UserDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq(""),
                eq(HttpMethod.POST),
                argThat(entity -> entity.getBody() == userDto),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = userClient.addUser(userDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq(""),
                eq(HttpMethod.POST),
                argThat(entity -> entity.getBody() == userDto),
                eq(Object.class)
        );
    }

    @Test
    @DisplayName("Выполнить обновление данных пользователя")
    void updateUser_shouldCallPatchWithCorrectParameters() {
        // Given
        long userId = 1L;
        UserDto userDto = new UserDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/{userId}"),
                eq(HttpMethod.PATCH),
                argThat(entity -> entity.getBody() == userDto),
                eq(Object.class),
                eq(Map.of("userId", userId))
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = userClient.updateUser(userId, userDto);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/{userId}"),
                eq(HttpMethod.PATCH),
                argThat(entity -> entity.getBody() == userDto),
                eq(Object.class),
                eq(Map.of("userId", userId))
        );
    }

    @Test
    @DisplayName("Получить данные пользователя по идентификатору")
    void getUserById_shouldCallGetWithCorrectPath() {
        // Given
        long userId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/{userId}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class),
                eq(Map.of("userId", userId))
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = userClient.getUserById(userId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/{userId}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class),
                eq(Map.of("userId", userId))
        );
    }

    @Test
    @DisplayName("Выполнить удаление пользователя")
    void deleteUser_shouldCallDeleteWithCorrectPath() {
        // Given
        long userId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(restTemplate.exchange(
                eq("/" + userId),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        // When
        ResponseEntity<Object> actualResponse = userClient.deleteUser(userId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).exchange(
                eq("/" + userId),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Object.class)
        );
    }
}