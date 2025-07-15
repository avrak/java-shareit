package ru.practicum.shareit.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseClientTest {

    @Mock
    private RestTemplate restTemplate;

    private BaseClient baseClient;

    @BeforeEach
    void setUp() {
        baseClient = new BaseClient(restTemplate);
    }

    @Test
    @DisplayName("Выполнить GET запрос без параметров")
    void get_shouldCallExchangeWithCorrectParameters() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.get("/test", 1L);

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить GET запрос с параметрами")
    void get_withParameters_shouldPassParametersToExchange() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        Map<String, Object> parameters = Map.of("param", "value");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class), eq(parameters)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.get("/test", 1L, parameters);

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class), eq(parameters));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить POST запрос с телом")
    void post_shouldCallExchangeWithCorrectParameters() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.post("/test", 1L, "test body");

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить PUT запрос с телом")
    void put_shouldCallExchangeWithCorrectParameters() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.put("/test", 1L, "test body");

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить PATCH запрос с телом")
    void patch_shouldCallExchangeWithCorrectParameters() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.patch("/test", 1L, "test body");

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить DELETE запрос")
    void delete_shouldCallExchangeWithCorrectParameters() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.delete("/test", 1L);

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Обработать ошибку HTTP запроса")
    void makeAndSendRequest_shouldHandleHttpError() {
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenThrow(exception);

        ResponseEntity<Object> response = baseClient.get("/test", 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Выполнить GET запрос без userId и параметров")
    void get_withoutUserIdAndParameters_shouldCallExchange() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.get("/test");

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить POST запрос без userId и параметров")
    void post_withoutUserIdAndParameters_shouldCallExchange() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.post("/test", "test body");

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить PATCH запрос без userId и параметров")
    void patch_withoutUserIdAndParameters_shouldCallExchange() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.patch("/test", "test body");

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить PATCH запрос только с userId")
    void patch_withUserIdOnly_shouldCallExchange() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.patch("/test", 1L);

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Выполнить DELETE запрос без userId и параметров")
    void delete_withoutUserIdAndParameters_shouldCallExchange() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.delete("/test");

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Подготовить ответ при ошибке с телом ответа")
    void prepareGatewayResponse_withErrorAndBody_shouldReturnResponse() {
        ResponseEntity<Object> errorResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error message");

        ResponseEntity<Object> result = BaseClient.prepareGatewayResponse(errorResponse);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error message", result.getBody());
    }

    @Test
    @DisplayName("Подготовить ответ при ошибке без тела ответа")
    void prepareGatewayResponse_withErrorAndNoBody_shouldReturnResponse() {
        ResponseEntity<Object> errorResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ResponseEntity<Object> result = BaseClient.prepareGatewayResponse(errorResponse);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }
}