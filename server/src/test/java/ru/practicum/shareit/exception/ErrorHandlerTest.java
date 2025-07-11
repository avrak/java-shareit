package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.model.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void handleParameterNotValidException_shouldReturnBadRequest() {
        String reason = "Invalid parameter value";
        ParameterNotValidException exception = new ParameterNotValidException(reason);

        ErrorResponse response = errorHandler.handleParameterNotValidException(exception);

        assertNotNull(response);
        assertEquals("Ошибка ввода", response.getError());
        assertEquals(reason, response.getDescription());
    }

    @Test
    void handleNotFoundException_shouldReturnNotFound() {
        String reason = "Resource not found";
        NotFoundException exception = new NotFoundException(reason);

        ErrorResponse response = errorHandler.handleNotFoundException(exception);

        assertNotNull(response);
        assertEquals("Ресурс не найден", response.getError());
        assertEquals(reason, response.getDescription());
    }

    @Test
    void handleConflictException_shouldReturnConflict() {
        String reason = "Data conflict occurred";
        ConflictException exception = new ConflictException(reason);

        ErrorResponse response = errorHandler.handleConflictException(exception);

        assertNotNull(response);
        assertEquals("Конфликт данных", response.getError());
        assertEquals(reason, response.getDescription());
    }

    @Test
    void handleForbiddenException_shouldReturnForbidden() {
        String reason = "Access denied";
        ForbiddenException exception = new ForbiddenException(reason);

        ErrorResponse response = errorHandler.handleForbiddenException(exception);

        assertNotNull(response);
        assertEquals("Запрещено", response.getError());
        assertEquals(reason, response.getDescription());
    }

    @Test
    void handleInternalServerException_shouldReturnInternalServerError() {
        String message = "Unexpected error occurred";
        Throwable exception = new RuntimeException(message);

        ErrorResponse response = errorHandler.handleInternalServerException(exception);

        assertNotNull(response);
        assertEquals("Внутренняя ошибка", response.getError());
        assertEquals(message, response.getDescription());
    }

    @Test
    void handleInternalServerException_withNullMessage_shouldReturnDefaultMessage() {
        Throwable exception = new RuntimeException();

        ErrorResponse response = errorHandler.handleInternalServerException(exception);

        assertNotNull(response);
        assertEquals("Внутренняя ошибка", response.getError());
    }
}
