package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "items")
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
    private Long owner; // владелец вещи;

    @Column(name = "request_id")
    private Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
}
