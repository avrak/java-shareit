package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
@Getter
@Setter
@Validated
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // уникальный идентификатор запроса;

    @Column(name = "description", nullable = false)
    @NotBlank
    String description; // текст запроса, содержащий описание требуемой вещи;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestor_id")
    User requestor; // пользователь, создавший запрос;

    LocalDateTime createdAt; // дата и время создания запроса.

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id")
    private Collection<Item> items;
}
