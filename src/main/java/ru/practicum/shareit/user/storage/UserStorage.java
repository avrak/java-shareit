package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.exception.model.ParameterNotValidException;

import java.util.HashMap;

@Component
public class UserStorage {
    private static long userId;
    private final HashMap<Long, User> userListById;
    private final HashMap<String, User> userListByMail;

    public UserStorage () {
        userListById = new HashMap<>();
        userListByMail = new HashMap<>();
    }

    private long getNewUserId() {
        return userId++;
    }

    public User saveUser(User user) {

        userListById.put(user.getId(), user);
        userListByMail.put(user.getEmail(), user);

        return user;
    }

    public User getUserById(Long userId) {
        if (!userListById.containsKey(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не существует");
        }

        return userListById.get(userId);
    }

    public boolean checkMailExists(String email) {
        return userListByMail.containsKey(email);
    }

    public void deleteUserById(Long userId) {
        userListByMail.remove(userListById.get(userId).getEmail());
        userListById.remove(userId);

    }
}
