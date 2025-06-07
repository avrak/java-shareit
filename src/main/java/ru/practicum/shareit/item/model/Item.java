package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Название вещи должно быть указано")
    @Column(name = "name")
    String name; // краткое название;

    @NotNull(message = "Описание вещи должно быть указано")
    @Column(name = "description")
    String description; // развёрнутое описание;

    @NotNull(message = "Доступность вещи должна быть указана")
    @Column(name = "available")
    Boolean available; // статус о том, доступна или нет вещь для аренды;

    @Column(name = "owner_id")
    Long owner; // владелец вещи;

    @Column(name = "request_id")
    Long request; // если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
}
