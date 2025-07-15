package ru.practicum.shareit.user.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Сохранение пользователя")
    void saveUser_test() {
        User newUser = new User(null, "saveUser_test", "saveUser_test@example.com");

        User savedUser = userRepository.save(newUser);

        assertNotNull(savedUser.getId());
    }

    @Test
    @DisplayName("Поиск пользователя по Id")
    void findUserById_testByExistId() {
        String userName = "findUserById_testByExistId";
        String email = userName + "@example.com";

        User newUser = new User(null, userName, email);

        User savedUser = userRepository.save(newUser);

        Optional<User> foundUser = userRepository.findUserById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo(userName);
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Проверка на несуществующего пользователя по Id")
    void findUserById_testByNonExistsId() {
        Optional<User> foundUser = userRepository.findUserById(-1L);

        assertTrue(foundUser.isEmpty());
    }

    @Test
    @DisplayName("Проверка пользователя по email")
    void existsByEmail_testByExistEmail() {
        String userName = "existsByEmail_testByExistEmail";
        String email = userName + "@example.com";

        User newUser = new User(null, userName, email);

        userRepository.save(newUser);

        assertTrue(userRepository.existsByEmail(email));
    }

    @Test
    @DisplayName("Проверка на несуществующего пользователя по email")
    void findUserById_testByNonExistsEmail() {
        assertFalse(userRepository.existsByEmail("findUserById_testByNonExistsEmail@example.com"));
    }
}
