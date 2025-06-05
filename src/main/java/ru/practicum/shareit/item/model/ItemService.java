package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDateTimeDto;

import java.util.Collection;

public interface ItemService {
    public ItemWithDateTimeDto getItemById(Long itemId);

    public ItemDto addItem(Long userId, ItemDto itemDto);

    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    public Collection<ItemWithDateTimeDto> getItemListByOwner(Long ownerId);

    public Collection<ItemWithDateTimeDto> getItemListByText(String text);

    public void deleteItem(Long itemId);

    public ItemWithDateTimeDto getItemWithComments(Long itemId);
}
