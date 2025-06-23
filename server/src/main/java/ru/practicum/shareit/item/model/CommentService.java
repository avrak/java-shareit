package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.CommentDto;

import java.util.Collection;

public interface CommentService {
    CommentDto addComment(Long userId, Long itemId, CommentDto comment);

    Collection<CommentDto> findCommentsByItemId(Long itemId);

    Collection<CommentDto> findCommentByUserId(Long userId);
}
