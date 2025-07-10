package ru.practicum.shareit.booking.service;

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
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    Booking booking;
    BookingDto bookingDto;
    User user;
    UserDto userDto;
    Item item;
    ItemDto itemDto;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    BookingServiceImpl bookingService;

    @BeforeEach
    public void beforeEach() {
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
                LocalDateTime.now().plusMinutes(10L),
                LocalDateTime.now().plusMinutes(20L),
                1L,
                1L,
                Statuses.WAITING.name(),
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
    }

    @Test
    @DisplayName("Сохранить бронирование с корректными данными")
    void saveBooking_withCorrectData() {
        when(userRepository.findUserById(any(Long.class))).thenReturn(Optional.of(user));
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.of(item));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        assertEquals(bookingDto, bookingService.saveBooking(1L, bookingDto));
        verify(userRepository).findUserById(any(Long.class));
        verify(itemRepository).findItemById(any(Long.class));
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    @DisplayName("Сохранить бронирование с некорректными пользователем")
    void saveBooking_withIncorrectUser() {
        when(userRepository.findUserById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.saveBooking(1L, bookingDto));
        verify(userRepository).findUserById(any(Long.class));
    }

    @Test
    @DisplayName("Сохранить бронирование с некорректной вещью")
    void saveBooking_withIncorrectItem() {
        when(userRepository.findUserById(any(Long.class))).thenReturn(Optional.of(user));
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.saveBooking(1L, bookingDto));
        verify(userRepository).findUserById(any(Long.class));
        verify(itemRepository).findItemById(any(Long.class));
    }

    @Test
    @DisplayName("Корректное подтверждение бронирования")
    void approveBookingById_existingBooking() {
        BookingDto expectedDto = new BookingDto(
                1L,
                booking.getStart(),
                booking.getEnd(),
                1L,
                1L,
                itemDto,
                userDto,
                Statuses.APPROVED
        );

        when(bookingRepository.findBookingById(any(Long.class))).thenReturn(Optional.of(booking));
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.of(item));

        assertEquals(expectedDto, bookingService.approveBookingById(1L, 1L, true));
        verify(bookingRepository).findBookingById(any(Long.class));
        verify(itemRepository).findItemById(any(Long.class));
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    @DisplayName("Подтверждение несуществующего бронирования")
    void approveBookingById_notExistingBooking() {
        when(bookingRepository.findBookingById(any(Long.class))).thenReturn(Optional.of(booking));
        when(itemRepository.findItemById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.approveBookingById(1L, 1L, true));
        verify(bookingRepository).findBookingById(any(Long.class));
        verify(bookingRepository).findBookingById(any(Long.class));
    }

    @Test
    @DisplayName("Подтверждение бронирования несуществующей вещи")
    void approveBookingById_notExistingItem() {
        when(bookingRepository.findBookingById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.approveBookingById(1L, 1L, true));
        verify(bookingRepository).findBookingById(any(Long.class));
    }

    @Test
    @DisplayName("Найти бронирование по id")
    void getBookingById_existing() {
        when(bookingRepository.findBookingById(any(Long.class))).thenReturn(Optional.of(booking));

        assertEquals(bookingDto, bookingService.getBookingById(1L, 1L));
        verify(bookingRepository).findBookingById(any(Long.class));
    }

    @Test
    @DisplayName("Найти несуществующее бронирование по id")
    void getBookingById_notExisting() {
        when(bookingRepository.findBookingById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.getBookingById(1L, 1L));
        verify(bookingRepository).findBookingById(any(Long.class));
    }

    @Test
    @DisplayName("Найти бронирования пользователя")
    void getBookingListByBookerId_existingUser() {
        when(bookingRepository.findBookingByBookerId(any(Long.class)))
                .thenReturn(new ArrayList<>(Collections.singletonList(booking)));

        assertEquals(1, bookingService.getBookingListByBookerId(1L).size());
        verify(bookingRepository).findBookingByBookerId(any(Long.class));
    }

    @Test
    @DisplayName("Найти бронирования по их статусу и пользователю")
    void getBookingListByOwnerIdAndStatus_existingUser() {
        when(bookingRepository.findBookingListByOwnerId(any(Long.class)))
                .thenReturn(new ArrayList<>(Collections.singletonList(booking)));

        assertEquals(1,
                bookingService.getBookingListByOwnerIdAndStatus(1L, Statuses.APPROVED.name()).size());
        verify(bookingRepository).findBookingListByOwnerId(any(Long.class));
    }
}
