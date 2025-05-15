package ru.practicum.shareit.user.model;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    public UserDto addUser(UserDto user);

    public UserDto updateUser(Long userId, UserDto user);

    public void deleteUser(Long userId);

    public UserDto getUserById(Long userId);
}
