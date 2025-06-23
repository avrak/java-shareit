package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWideDto;
import ru.practicum.shareit.item.service.CommentServiceImpl;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemServiceImpl itemService;
    private final CommentServiceImpl commentService;

    @PostMapping
    public ItemDto addItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody @Valid ItemDto itemDto
    ) {
        log.info("Создать вещь");

        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable(value = "itemId") Long itemId,
            @RequestBody ItemDto itemDto
    ) {
        log.info("Изменить вещь с id={}", itemId);

        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemWideDto getItemById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable(value = "itemId") Long itemId
    ) {
        log.info("Показать вещь по id={} пользователю {}", itemId, userId);

        return itemService.getItemWithComments(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Collection<ItemWideDto>> getItemListByOwnerId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Показать все вещи пользователя с id={}", userId);

        return ResponseEntity.ok().body(itemService.getItemListByOwner(userId));
    }

    @GetMapping("/search")
    public Collection<ItemWideDto> getItemListByText(@RequestParam(name = "text", required = true) String text) {
        log.info("Показать все вещи с текстом={}", text);

        return itemService.getItemListByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable(value = "itemId") Long itemId,
            @RequestBody CommentDto commentDto
    ) {
        log.info("Добавить комментарий пользователя {} к вещи {}", userId, commentDto.getItemId());

        return commentService.addComment(userId, itemId, commentDto);
    }
}
