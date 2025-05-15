package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.model.ForbiddenException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemService;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private long newItemId = 0;

    @Override
    public ItemDto getItemById(Long itemId) {
        return ItemMapper.toItemDto(itemStorage.getItemById(itemId));
    }

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        if (itemDto.getOwner() == null) {
            itemDto.setOwner(userId);
        } else if (!userId.equals(itemDto.getOwner())) {
            throw new ForbiddenException("Только владелец может добавить вещь!");
        }

        User user = userStorage.getUserById(itemDto.getOwner());

        itemDto.setId(newItemId++);
        return saveItem(itemDto);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        ItemDto currentItemDto = ItemMapper.toItemDto(itemStorage.getItemById(itemId));

        if (!userId.equals(currentItemDto.getOwner())) {
            throw new ForbiddenException("Только владелец может обновить вещь!");
        }

        if (itemDto.getName() != null) {
            currentItemDto.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            currentItemDto.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            currentItemDto.setAvailable(itemDto.getAvailable());
        }

        return saveItem(itemDto);
    }

    private ItemDto saveItem(ItemDto itemDto) {
        return ItemMapper.toItemDto(itemStorage.saveItem(ItemMapper.toItem(itemDto)));
    }

    @Override
    public Collection<ItemDto> getItemListByOwner(Long ownerId) {
        Collection<ItemDto> itemDtoList = new ArrayList<>();

        for (Item item : itemStorage.getItemListByOwnerId(ownerId)) {
            itemDtoList.add(ItemMapper.toItemDto(item));
        }

        return itemDtoList;
    }

    @Override
    public Collection<ItemDto> getItemListByText(String text) {
        Collection<ItemDto> itemDtoList = new ArrayList<>();

        for (Item item : itemStorage.getItemListByText(text)) {
            itemDtoList.add(ItemMapper.toItemDto(item));
        }

        return itemDtoList;
    }

    @Override
    public void deleteItem(Long itemId) {
        itemStorage.deleteItemById(itemId);
    }
}
