package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserClientController {
    private final UserClient userClient;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") Long userId) {
        log.info("Получить пользователя с id={}", userId);

        return userClient.getUserById(userId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto user) {
        log.info("Создать пользователя");

        return userClient.addUser(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "userId", required = true) Long userId,
            @RequestBody UserDto userDto
    ) {
        log.info("Изменить пользователя с id={}", userId);

        return userClient.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "userId", required = true) Long userId) {
        log.info("Удалить пользователя с id={}", userId);

        return userClient.deleteUser(userId);
    }
}
