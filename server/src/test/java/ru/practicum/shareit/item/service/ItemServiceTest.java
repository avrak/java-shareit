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
import ru.practicum.shareit.exception.model.ForbiddenException;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.dto.ItemWideDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    private final static String name = "UserServiceTest";
    private final static String email = name + "@example.com";
    private final LocalDateTime createdAt = LocalDateTime.now();

    User user;
    UserDto userDto;
    Item item = new Item();
    ItemDto itemDto;
    ItemWideDto itemWideDto;
    Booking bookingLast;
    BookingDto bookingLastDto;
    Booking bookingNext;
    BookingDto bookingNextDto;
    Comment comment;
    CommentDto commentDto;
    ItemRequest itemRequest;
    ItemRequestDto itemRequestDto;


    @Mock
    BookingRepository bookingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ItemRequestRepository itemRequestRepository;

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemServiceImpl itemService;


    @BeforeEach
    void beforeEach() {
        user = new User(1L, name, email);
        userDto = new UserDto(1L, name, email);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime startLast = LocalDateTime.now().minus(Duration.ofSeconds(10L));
        LocalDateTime endLast = LocalDateTime.now().minus(Duration.ofSeconds(1L));
        LocalDateTime startNext = LocalDateTime.now().plus(Duration.ofSeconds(1L));
        LocalDateTime endNext = LocalDateTime.now().plus(Duration.ofSeconds(10L));

        item = new Item();
        item.setId(1L);
        item.setName("ItemServiceTest");
        item.setDescription("ItemServiceTest description");
        item.setOwnerId(1L);
        item.setAvailable(true);
        item.setRequestId(20L);
        item.setComments(null);

        itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("ItemServiceTest");
        itemDto.setDescription("ItemServiceTest description");
        itemDto.setOwnerId(1L);
        itemDto.setAvailable(true);
        itemDto.setRequestId(20L);

        bookingLast = new Booking();
        bookingLast.setId(1L);
        bookingLast.setItem(item);
        bookingLast.setBooker(user);
        bookingLast.setStatus(Statuses.APPROVED.name());
        bookingLast.setStart(startLast);
        bookingLast.setEnd(endLast);

        bookingLastDto = new BookingDto();
        bookingLastDto.setId(1L);
        bookingLastDto.setItem(itemDto);
        bookingLastDto.setBooker(userDto);
        bookingLastDto.setStatus(Statuses.APPROVED);
        bookingLastDto.setStart(startLast);
        bookingLastDto.setEnd(endLast);

        bookingNext = new Booking();
        bookingNext.setId(1L);
        bookingNext.setItem(item);
        bookingNext.setBooker(user);
        bookingNext.setStatus(Statuses.APPROVED.name());
        bookingNext.setStart(startNext);
        bookingNext.setEnd(endNext);

        bookingNextDto = new BookingDto();
        bookingNextDto.setId(1L);
        bookingNextDto.setItem(itemDto);
        bookingNextDto.setBooker(userDto);
        bookingNextDto.setStatus(Statuses.APPROVED);
        bookingNextDto.setStart(startNext);
        bookingNextDto.setEnd(endNext);

        comment = new Comment();
        comment.setId(1L);
        comment.setItemId(1L);
        comment.setItem(item);
        comment.setUser(1L);
        comment.setAuthor(user);
        comment.setCreatedAt(createdAt);
        comment.setText("ItemServiceTest");

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setItemId(1L);
        commentDto.setUser(1L);
        commentDto.setAuthorName(user.getName());
        commentDto.setCreated(createdAt);
        commentDto.setText("ItemServiceTest");

        item.setComments(List.of(comment));

        itemWideDto = new ItemWideDto();
        itemWideDto.setId(1L);
        itemWideDto.setName("ItemServiceTest");
        itemWideDto.setDescription("ItemServiceTest description");
        itemWideDto.setOwner(1L);
        itemWideDto.setAvailable(true);
        itemWideDto.setRequest(20L);
        itemWideDto.setLastBooking(bookingLastDto);
        itemWideDto.setNextBooking(bookingNextDto);
        itemWideDto.setComments(List.of(commentDto));

        itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setItems(List.of(item));
        itemRequest.setRequestor(user);
        itemRequest.setDescription("ItemServiceTest request");
        itemRequest.setCreatedAt(createdAt);

        itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(1L);
        itemRequestDto.setItems(List.of(new ItemShortDto()));
        itemRequestDto.setRequestor(userDto);
        itemRequestDto.setDescription("ItemServiceTest request");
        itemRequestDto.setItems(List.of(new ItemShortDto(1L, "ItemServiceTest", "ItemServiceTest description", true, 1L)));
        itemRequestDto.setCreated(createdAt);

        itemDto.setRequest(itemRequestDto);
        item.setRequest(itemRequest);
    }

    @Test
    @DisplayName("Найти существующую вещь")
    void getItemById_existing() {
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.of(item));
        when(bookingRepository.findFirstOneByItemIdAndStatusAndEndBeforeOrderByEndDesc(
                any(Long.class),
                any(String.class),
                any(LocalDateTime.now().getClass()))
        ).thenReturn(Optional.of(bookingLast));
        when(bookingRepository.findFirstOneByItemIdAndStatusAndStartAfterOrderByStartAsc(
                any(Long.class),
                any(String.class),
                any(LocalDateTime.now().getClass()))
        ).thenReturn(Optional.of(bookingNext));

        assertEquals(itemWideDto, itemService.getItemById(1L));

        verify(itemRepository).findItemById(1L);

        verify(bookingRepository).findFirstOneByItemIdAndStatusAndEndBeforeOrderByEndDesc(
                eq(1L), any(String.class), any(LocalDateTime.now().getClass())
        );

        verify(bookingRepository).findFirstOneByItemIdAndStatusAndStartAfterOrderByStartAsc(
                eq(1L), any(String.class), any(LocalDateTime.now().getClass())
        );

    }

    @Test
    @DisplayName("Найти несуществующую вещь")
    void getItemById_nonExisting() {
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.getItemById(1L));
        verify(itemRepository).findItemById(1L);
    }

    @Test
    @DisplayName("Сохранить вещь несуществующего пользователя")
    void addItem_nonExistingUser() {
        when(userRepository.findUserById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.addItem(1L, itemDto));
        verify(userRepository).findUserById(any(Long.class));
    }

    @Test
    @DisplayName("Сохранить вещь по несуществующему запросу")
    void addItem_nonExistingRequest() {
        when(userRepository.findUserById(any(Long.class))).thenReturn(Optional.of(user));
        when(itemRequestRepository.findItemRequestById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.addItem(1L, itemDto));
        verify(userRepository).findUserById(any(Long.class));
        verify(itemRequestRepository).findItemRequestById(any(Long.class));
    }

    @Test
    @DisplayName("Сохранить вещь с корректными параметрами")
    void addItem_correctly() {
        when(userRepository.findUserById(any(Long.class))).thenReturn(Optional.of(user));
        when(itemRequestRepository.findItemRequestById(any(Long.class))).thenReturn(Optional.of(itemRequest));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        assertEquals(itemDto, itemService.addItem(1L, itemDto));
        verify(userRepository).findUserById(any(Long.class));
        verify(itemRequestRepository).findItemRequestById(any(Long.class));
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    @DisplayName("Обновить несуществующую вещь")
    void updateItem_nonExisting() {
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.updateItem(1L, 1L, itemDto));
        verify(itemRepository).findItemById(any(Long.class));
    }

    @Test
    @DisplayName("Обновить вещь не от имени владельца")
    void updateItem_notByOwner() {
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.of(item));

        assertThrows(ForbiddenException.class, () -> itemService.updateItem(100L, 1L, itemDto));
        verify(itemRepository).findItemById(any(Long.class));
    }

    @Test
    @DisplayName("Обновить вещь с корректными данными")
    void updateItem_correctly() {
        ItemDto itemUpdatedDto = new ItemDto();
        itemUpdatedDto.setId(1L);
        itemUpdatedDto.setName("ItemServiceTest upd");
        itemUpdatedDto.setDescription("ItemServiceTest description upd");
        itemUpdatedDto.setOwnerId(1L);
        itemUpdatedDto.setAvailable(true);
        itemUpdatedDto.setRequestId(20L);

        Item itemUpdated = new Item();
        itemUpdated.setId(1L);
        itemUpdated.setName("ItemServiceTest upd");
        itemUpdated.setDescription("ItemServiceTest description upd");
        itemUpdated.setOwnerId(1L);
        itemUpdated.setAvailable(true);
        itemUpdated.setRequestId(20L);


        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(itemUpdated);

        assertEquals(itemUpdatedDto, itemService.updateItem(1L, 1L, itemUpdatedDto));
        verify(itemRepository).findItemById(any(Long.class));
        verify(itemRepository).save(any(Item.class));
    }

}