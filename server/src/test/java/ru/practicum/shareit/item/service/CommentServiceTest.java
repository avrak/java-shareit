package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.model.ParameterNotValidException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookingRepository bookingRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    Comment comment;
    CommentDto commentDto;
    Booking booking;
    BookingDto bookingDto;
    User user;
    UserDto userDto;
    Item item;
    ItemDto itemDto;

    @BeforeEach
    public void beforeEach() {
        LocalDateTime createdAt = LocalDateTime.now();

        user = new User(
                1L,
                "BookingServiceTest",
                "BookingServiceTest@example.com"
        );

        userDto = new UserDto(
                1L,
                "BookingServiceTest",
                "BookingServiceTest@example.com"
        );

        item = new Item(
                1L,
                "BookingServiceTest",
                "BookingServiceTest description",
                true,
                user.getId(),
                null,
                null,
                null
        );

        itemDto = new ItemDto(
                1L,
                "BookingServiceTest",
                "BookingServiceTest description",
                true,
                user.getId(),
                null,
                null
        );

        booking = new Booking(
                1L,
                LocalDateTime.now().minusMinutes(10L),
                LocalDateTime.now().minusMinutes(20L),
                1L,
                1L,
                Statuses.APPROVED.name(),
                item,
                user
        );

        bookingDto = new BookingDto(
                1L,
                booking.getStart(),
                booking.getEnd(),
                1L,
                1L,
                itemDto,
                userDto,
                Statuses.WAITING
        );

        comment = new Comment(
                1L,
                1L,
                1L,
                createdAt,
                "CommentServiceTest",
                item,
                user
        );

        commentDto = new CommentDto(
                1L,
                1L,
                1L,
                createdAt,
                comment.getAuthor().getName(),
                "CommentServiceTest"
        );
    }

    @Test
    @DisplayName("Создать корректный комментарий")
    void addComment_correctly() {
        when(bookingRepository.findByItemIdAndBookerId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(booking));
        when(userRepository.findUserById(any(Long.class))).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDto savedCommentDto = commentService.addComment(1L, 1L, commentDto);

        assertEquals(commentDto.getUser(), savedCommentDto.getUser());
        assertEquals(commentDto.getText(), savedCommentDto.getText());
        verify(bookingRepository).findByItemIdAndBookerId(any(Long.class), any(Long.class));
        verify(userRepository).findUserById(any(Long.class));
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("Создать комментарий пользователя, не использовавшего вещь")
    void addComment_byWrongUser() {
        when(bookingRepository.findByItemIdAndBookerId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ParameterNotValidException.class,
                () -> commentService.addComment(1L, 1L, commentDto));
        verify(bookingRepository).findByItemIdAndBookerId(any(Long.class), any(Long.class));
    }

    @Test
    @DisplayName("Найти комментарии по id существующей вещи")
    void findCommentsByItemId_existing() {
        when(commentRepository.findCommentsByItemId(any(Long.class)))
                .thenReturn(new ArrayList<>(Collections.singletonList(comment)));

        assertEquals(1, commentService.findCommentsByItemId(1L).size());
        verify(commentRepository).findCommentsByItemId(any(Long.class));
    }

    @Test
    @DisplayName("Найти комментарии по id несуществующей вещи")
    void findCommentsByItemId_nonExisting() {
        when(commentRepository.findCommentsByItemId(any(Long.class)))
                .thenReturn(new ArrayList<>());

        assertEquals(0, commentService.findCommentsByItemId(1L).size());
        verify(commentRepository).findCommentsByItemId(any(Long.class));
    }

    @Test
    @DisplayName("Найти комментарии существующего пользователя")
    void findCommentByUserId_existingUser() {
        when(commentRepository.findCommentsByUser(any(Long.class)))
                .thenReturn(new ArrayList<>(Collections.singletonList(comment)));

        assertEquals(1, commentService.findCommentByUserId(1L).size());
        verify(commentRepository).findCommentsByUser(any(Long.class));
    }

    @Test
    @DisplayName("Найти комментарии несуществующего пользователя")
    void findCommentByUserId_nonExistingUser() {
        when(commentRepository.findCommentsByUser(any(Long.class)))
                .thenReturn(new ArrayList<>());

        assertEquals(0, commentService.findCommentByUserId(1L).size());
        verify(commentRepository).findCommentsByUser(any(Long.class));
    }
}
