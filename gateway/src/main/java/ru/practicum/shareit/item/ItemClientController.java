package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collections;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemClientController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody @Valid ItemDto itemDto
    ) {
        log.info("Создать вещь");

        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable(value = "itemId") Long itemId,
            @RequestBody ItemDto itemDto
    ) {
        log.info("Изменить вещь с id={}", itemId);

        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable(value = "itemId") Long itemId
    ) {
        log.info("Показать вещь по id={} пользователю {}", itemId, userId);

        return itemClient.getItemById(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemListByOwnerId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Показать все вещи пользователя с id={}", userId);

        return itemClient.getItemListByOwnerId(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemListByText(@RequestParam(name = "text", required = true) String text) {
        log.info("Показать все вещи с текстом={}", text);

        return text.isBlank() ? ResponseEntity.ok(Collections.emptyList()) : itemClient.getItemListByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable(value = "itemId") Long itemId,
            @RequestBody CommentDto commentDto
    ) {
        log.info("Добавить комментарий пользователя {} к вещи {}", userId, commentDto.getItemId());

        return itemClient.addComment(userId, itemId, commentDto);
    }
}
