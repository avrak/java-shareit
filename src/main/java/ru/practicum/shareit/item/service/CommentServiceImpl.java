package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.model.CommentService;
import ru.practicum.shareit.item.storage.CommentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    public final CommentRepository commentRepository;

    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        commentDto.setUser(userId);
        commentDto.setItem(itemId);
        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toComment(commentDto)));
    }

    public Collection<CommentDto> findCommentsByItemId(Long itemId) {
        return commentRepository.findCommentsByItem(itemId)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public Collection<CommentDto> findCommentByUserId(Long userId) {
        return commentRepository.findCommentsByUser(userId)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
