package ru.practicum.shareit.item.model;

import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    public ItemDto getItemById(Long itemId);

    public ItemDto addItem(Long userId, ItemDto itemDto);

    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    public Collection<ItemDto> getItemListByOwner(Long ownerId);

    public Collection<ItemDto> getItemListByText(String text);

    public void deleteItem(Long itemId);
}
