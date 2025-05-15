package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.model.ConflictException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserService;
import ru.practicum.shareit.user.storage.UserStorage;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;
    private long newUserId;

    @Override
    public UserDto addUser(UserDto user) {
        if (userStorage.checkMailExists(user.getEmail())) {
            throw new ConflictException("Пользователь с email " + user.getEmail() + " уже существует");
        }
        user.setId(newUserId++);
        return saveUser(UserMapper.toUser(user));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto user) {
        User currentUser = userStorage.getUserById(userId);

        if (
                user.getEmail() != null
                && !user.getEmail().equals(currentUser.getEmail())
                && userStorage.checkMailExists(user.getEmail())
        ) {
            throw new ConflictException("Пользователь с email " + user.getEmail() + " уже существует");
        }

        if (user.getEmail() != null) {
            currentUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            currentUser.setName(user.getName());
        }

        return saveUser(currentUser);
    }


    private UserDto saveUser(User user) {
        return UserMapper.toUserDto(userStorage.saveUser(user));
    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserMapper.toUserDto(userStorage.getUserById(userId));
    }

    @Override
    public void deleteUser(Long userId) {
        userStorage.deleteUserById(userId);
        for (Item item : itemStorage.getItemListByOwnerId(userId)) {
            itemStorage.deleteItemById(item.getId());
        }
    }

}
