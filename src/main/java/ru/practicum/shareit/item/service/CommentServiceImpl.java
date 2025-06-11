package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.model.ParameterNotValidException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentService;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    public final CommentRepository commentRepository;
    public final UserRepository userRepository;
    public final BookingRepository bookingRepository;

    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        Optional<Booking> bookingOptional = bookingRepository.findByItemIdAndBookerId(itemId, userId);
        if (bookingOptional.isEmpty()
            || bookingOptional.get().getEnd().isAfter(LocalDateTime.now())
            || !bookingOptional.get().getStatus().equals(Statuses.APPROVED.name())
        ) {
            throw new ParameterNotValidException("Только пользователи использовавшие вещь могут оставлять комментарии");
        }

        commentDto.setUser(userId);
        commentDto.setItemId(itemId);
        commentDto.setCreated(LocalDateTime.now());
        Comment comment = CommentMapper.toComment(commentDto);
        comment.setAuthor(userRepository.findUserById(userId).get());

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    public Collection<CommentDto> findCommentsByItemId(Long itemId) {
        return commentRepository.findCommentsByItemId(itemId)
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
