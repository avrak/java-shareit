package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.model.ConflictException;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final String name = "UserServiceTest";
    private final String email = name + "@example.com";

    User user = new User(1L, name, email);
    UserDto userDto = new UserDto(1L, name, email);

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("Сохранить пользователя с корректным email")
    void addUser_withCorrectEmail() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        assertEquals(userDto, userService.addUser(userDto));
        verify(userRepository).existsByEmail(any());
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("Сохранить пользователя с ранее сохранённым email")
    void addUser_withUtilizedEmail() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.addUser(userDto));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Обновить пользователя")
    void updateUser_withCorrectValues() {
        User updatedUser = new User(1L, "updatedUser", "updated@user.com");
        UserDto updatedUserDto = new UserDto(1L, "updatedUser", "updated@user.com");

        when(userRepository.findUserById(1L)).thenReturn((Optional.of(updatedUser)));
        when(userRepository.save(any())).thenReturn(updatedUser);

        assertEquals(updatedUserDto, userService.updateUser(1L, updatedUserDto));
        verify(userRepository).findUserById(any());
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("Обновить несуществующего пользователя")
    void updateUser_thatNotExists() {
        when(userRepository.findUserById(any())).thenReturn((Optional.empty()));

        assertThrows(NotFoundException.class, () -> userService.updateUser(1L, userDto));
        verify(userRepository).findUserById(any());
        verify(userRepository, never()).existsByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Обновить пользователя с ранее сохранённым email")
    void updateUser_withUtilizedEmail() {
        User updatedUser = new User(1L, "updatedUser", "updated@user.com");
        when(userRepository.findUserById(1L)).thenReturn((Optional.of(updatedUser)));
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.updateUser(1L, userDto));
        verify(userRepository).findUserById(any());
        verify(userRepository).existsByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Найти пользователя по id")
    void getUserById_thatExists() {
        when(userRepository.findUserById(any())).thenReturn(Optional.of(user));

        assertEquals(userDto, userService.getUserById(1L));
        verify(userRepository).findUserById(any());
    }

    @Test
    @DisplayName("Найти несуществующего пользователя по id")
    void getUserById_thatNotExists() {
        when(userRepository.findUserById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository).findUserById(any());
    }

    @Test
    @DisplayName("Удалить пользователя")
    void deleteUser_validUser() {
        userRepository.deleteUserById(1L);
        verify(userRepository).deleteUserById(any());
    }
}
