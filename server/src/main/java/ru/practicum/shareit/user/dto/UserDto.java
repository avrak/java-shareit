package ru.practicum.shareit.user.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id; // уникальный идентификатор пользователя;
    private String name; // имя или логин пользователя;
    @Email(message = "Некорректный имейл")
    @NotBlank(message = "Имейл должен быть указан")
    private String email; // адрес электронной почты (учтите, что два пользователя не могут иметь одинаковый адрес электронной почты).
}
