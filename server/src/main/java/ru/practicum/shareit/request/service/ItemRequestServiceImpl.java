package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestService;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserServiceImpl userService;

    @Override
    public ItemRequestDto addItemRequest(Long userId, ItemRequestDto itemRequestDto) {
        itemRequestDto.setRequestor(userService.getUserById(userId));
        itemRequestDto.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toRequestDto(itemRequestRepository.save(ItemRequestMapper.toRequest(itemRequestDto)));
    }

    @Override
    public Collection<ItemRequestDto> getMyItemRequests(Long userId) {
        return itemRequestRepository
                .findItemRequestListByRequestorId(userId)
                .stream()
                .map(ItemRequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemRequestDto> getAllItemRequests() {
        return itemRequestRepository
                .findAll()
                .stream()
                .map(ItemRequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getItemRequestById(Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findItemRequestById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с id=" + requestId + " не найден"));
        return ItemRequestMapper.toRequestDto(itemRequest);
    }
}
