package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.CommentDto;

import java.util.Collection;

public interface CommentService {
    public CommentDto addComment(Long userId, Long itemId, CommentDto comment);

    public Collection<CommentDto> findCommentsByItemId(Long itemId);

    public Collection<CommentDto> findCommentByUserId(Long userId);
}
