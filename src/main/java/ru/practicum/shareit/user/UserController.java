package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable(value = "userId") Long userId) {
        log.info("Получить пользователя с id={}", userId);

        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto user) {
        log.info("Создать пользователя");

        return userService.addUser(user);
    }
    @PatchMapping("/{userId}")
    public UserDto update(
            @PathVariable(value = "userId", required = true) Long userId,
            @RequestBody UserDto userDto
    ) {
        log.info("Изменить пользователя с id={}", userId);

        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable(value = "userId", required = true) Long userId) {
        log.info("Удалить пользователя с id={}", userId);

        userService.deleteUser(userId);
    }

}
