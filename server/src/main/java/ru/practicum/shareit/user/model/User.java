package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // уникальный идентификатор пользователя;

    @Column(name = "name")
    private String name; // имя или логин пользователя;

    @Email(message = "Некорректный имейл")
    @NotBlank(message = "Имейл должен быть указан")
    @Column(name = "email")
    private String email; // адрес электронной почты (учтите, что два пользователя не могут иметь одинаковый адрес электронной почты).
}
