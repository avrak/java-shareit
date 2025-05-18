package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Getter
@Setter
@Validated
public class User {
    Long id; // уникальный идентификатор пользователя;
    String name; // имя или логин пользователя;
    @Email(message = "Некорректный имейл")
    @NotBlank(message = "Имейл должен быть указан")
    String email; // адрес электронной почты (учтите, что два пользователя не могут иметь одинаковый адрес электронной почты).
}
