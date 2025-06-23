package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
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
@Table(name = "requests")
@Getter
@Setter
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // уникальный идентификатор запроса;

    @Column(name = "description", nullable = false)
    String description; // текст запроса, содержащий описание требуемой вещи;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestor_id")
    User requestor; // пользователь, создавший запрос;

    LocalDateTime createdAt; // дата и время создания запроса.

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id")
    private Collection<Item> items;
}
