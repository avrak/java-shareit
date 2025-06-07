package ru.practicum.shareit.user.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


//    public User saveUser(User user);

    public Optional<User> findUserById(Long userId);

    public boolean existsByEmail(String email);

    public void deleteUserById(Long userId);
}
