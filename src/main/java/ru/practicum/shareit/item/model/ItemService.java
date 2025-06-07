package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWideDto;

import java.util.Collection;

public interface ItemService {
    public ItemWideDto getItemById(Long itemId);

    public ItemDto addItem(Long userId, ItemDto itemDto);

    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    public Collection<ItemWideDto> getItemListByOwner(Long ownerId);

    public Collection<ItemWideDto> getItemListByText(String text);

    public void deleteItem(Long itemId);

    public ItemWideDto getItemWithComments(Long itemId);
}
