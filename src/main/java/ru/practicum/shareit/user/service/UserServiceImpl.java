package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.model.ConflictException;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;
import ru.practicum.shareit.user.model.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
//    private final UserStorage userStorage;
    private long newUserId;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public UserDto addUser(UserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Пользователь с email " + user.getEmail() + " уже существует");
        }
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(user)));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto user) {
        User currentUser = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + userId + " не найден"));

        if (
                user.getEmail() != null
                && !user.getEmail().equals(currentUser.getEmail())
                && userRepository.existsByEmail(user.getEmail())
        ) {
            throw new ConflictException("Пользователь с email " + user.getEmail() + " уже существует");
        }

        if (user.getEmail() != null) {
            currentUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            currentUser.setName(user.getName());
        }

        return UserMapper.toUserDto(userRepository.save(currentUser));
    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserMapper.toUserDto(
                userRepository.findUserById(userId).orElseThrow(
                        () -> new NotFoundException("Пользователь с id=" + userId + " не найден")
                )
        );
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
//        for (Item item : itemRepository.getItemListByOwnerId(userId)) {
//            itemRepository.deleteById(item.getId());
//        }
    }

}
