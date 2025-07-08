package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//@Validated
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // уникальный идентификатор вещи;

    @Column(name = "name", nullable = false)
    private String name; // краткое название;

    @Column(name = "description")
    private String description; // развёрнутое описание;

    @Column(name = "available", nullable = false)
    private Boolean available; // статус о том, доступна или нет вещь для аренды;

    @Column(name = "owner_id")
    private Long ownerId; // владелец вещи;

    @Column(name = "request_id")
    private Long requestId; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private ItemRequest request;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Collection<Comment> comments;

}
