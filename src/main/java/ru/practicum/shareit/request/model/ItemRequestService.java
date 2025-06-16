package ru.practicum.shareit.request.model;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestDto addItemRequest(Long userId, ItemRequestDto itemRequestDto);

    Collection<ItemRequestDto> getMyItemRequests(Long userId);

    Collection<ItemRequestDto> getAllItemRequests();

    ItemRequestDto getItemRequestById(Long requestId);
}
