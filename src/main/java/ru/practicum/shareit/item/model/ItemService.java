package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWideDto;

import java.util.Collection;

public interface ItemService {
    ItemWideDto getItemById(Long itemId);

    ItemDto addItem(Long userId, ItemDto itemDto);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    Collection<ItemWideDto> getItemListByOwner(Long ownerId);

    Collection<ItemWideDto> getItemListByText(String text);

    void deleteItem(Long itemId);

    ItemWideDto getItemWithComments(Long userId, Long itemId);
}
