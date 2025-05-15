package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.exception.model.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Component
public class ItemStorage {
    private final HashMap<Long, Item> itemListById;

    public ItemStorage() {
        itemListById = new HashMap<>();
    }

    public Item saveItem(Item item) {
        itemListById.put(item.getId(), item);
        return item;
    }

    public Item getItemById(Long itemId) {
        if (!itemListById.containsKey(itemId)) {
            throw new NotFoundException("Вещь с id=" + itemId + " не найдена.");
        }

        return itemListById.get(itemId);
    }

    public Collection<Item> getItemListByOwnerId(Long ownerId) {
        return new ArrayList<>(itemListById.values()
                .stream()
                .filter(item -> (item.getOwner() != null && item.getOwner().equals(ownerId)))
                .toList());
    }

    public Collection<Item> getItemListByText(String text) {
        Collection<Item> itemList = new ArrayList<>();

        if (text.isEmpty()) {
            return itemList;
        }

        itemList = itemListById.values()
                .stream()
                .filter(item -> (item.getName() + item.getDescription()).toLowerCase().contains(text.toLowerCase()))
                .filter(item -> (item.getAvailable().equals(Boolean.TRUE)))
                .toList();

        return itemList;
    }

    public void deleteItemById(Long itemId) {
        itemListById.remove(itemId);
    }
}