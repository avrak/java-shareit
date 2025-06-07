package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findItemById(Long itemId);

    public Collection<Item> findItemListByOwner(Long owner);

    @Query("select i from Item i where " +
            "i.available = true and " +
            "(upper(i.name) like upper(concat('%', :text, '%')) or " +
            "upper(i.description) like upper(concat('%', :text, '%')))")
    public Collection<Item> findItemListByText(@Param("text") String text);
}
