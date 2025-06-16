package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestService;

import java.util.Collection;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addItemRequest(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody @Valid ItemRequestDto itemRequestDto
    ) {
        log.info("Создать запрос");

        return itemRequestService.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getMyItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получить запросы пользователя с id={}", userId);

        return itemRequestService.getMyItemRequests(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пользователь с id={} получает все запросы", userId);

        return itemRequestService.getAllItemRequests();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable(value = "requestId") Long requestId
    ) {
        log.info("Пользователь с id={} получает запрос с id={}", userId, requestId);

        return itemRequestService.getItemRequestById(requestId);
    }

}
