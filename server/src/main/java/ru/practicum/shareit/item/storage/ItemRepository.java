package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemById(Long itemId);

    Collection<Item> findItemListByOwnerId(Long owner);

    @Query("select i from Item i where " +
            "i.available = true and " +
            "(upper(i.name) like upper(concat('%', :text, '%')) or " +
            "upper(i.description) like upper(concat('%', :text, '%')))")
    Collection<Item> findItemListByText(@Param("text") String text);
}
