package ru.practicum.shareit.user.dto;
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
    private String email; // адрес электронной почты (учтите, что два пользователя не могут иметь одинаковый адрес электронной почты).
}
