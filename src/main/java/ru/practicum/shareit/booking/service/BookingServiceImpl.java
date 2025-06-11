package ru.practicum.shareit.booking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingService;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.model.ForbiddenException;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.exception.model.ParameterNotValidException;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public BookingDto saveBooking(Long userId, BookingDto bookingDto) {

        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            throw new ParameterNotValidException("Время начала и окончания бронирования должны быть заданы");
        } else if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ParameterNotValidException("Время начала бронирования не может быть в прошлом");
        } else if (bookingDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new ParameterNotValidException("Время окончания бронирования не может быть в прошлом");
        } else if (bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new ParameterNotValidException("Время окончания бронирования не может раньше времени начала бронирования");
        } else if (bookingDto.getEnd().isEqual(bookingDto.getStart())) {
            throw new ParameterNotValidException("Время окончания бронирования не может совпадать со временем начала бронирования");
        }

        if (userId == null) {
            throw new ParameterNotValidException("Не передан автор бронирования");
        }

        User booker = userRepository
                .findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + bookingDto.getBooker() + " не найден"));

        Item item = itemRepository
                .findItemById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Вещь с id=" + bookingDto.getItemId() + " не найдена"));

        if (!item.getAvailable()) {
            throw new ParameterNotValidException("Вещь с id=" + bookingDto.getItemId() + " недоступна");
        }

        bookingDto.setBookerId(userId);
        bookingDto.setStatus(Statuses.WAITING);
        bookingDto.setItem(ItemMapper.toItemDto(item));
        bookingDto.setBooker(UserMapper.toUserDto(booker));

        return BookingMapper.toBookingDto(bookingRepository.save(BookingMapper.toBooking(bookingDto)));
    }

    @Override
    public BookingDto approveBookingById(Long userId, Long bookingId, Boolean status) {
        Booking booking = bookingRepository
                .findBookingById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с id=" + bookingId + " не найдено"));

        Item item = itemRepository
                .findItemById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Вещь с id=" + booking.getItem() + " не найдена"));

        if (!item.getOwner().equals(userId)) {
            throw new ForbiddenException("Только владелец вещи может подтвердить бронирование");
        }

        booking.setStatus(status ? Statuses.APPROVED.name() : Statuses.REJECTED.name());
        bookingRepository.save(booking);

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto getBookingById(Long userId, Long bookingId) {
        Booking booking = bookingRepository
                .findBookingById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с id=" + bookingId + " не найдено"));

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public Collection<BookingDto> getBookingListByBookerId(Long bookerId) {
        return bookingRepository.findBookingByBookerId(bookerId)
                .stream()
                .map(BookingMapper::toBookingDto)
                .toList();
    }

    @Override
    public Collection<BookingDto> getBookingListByOwnerIdAndStatus(Long ownerId, String status) {
        checkStatus(status);

        return bookingRepository.findBookingListByOwnerAndStatus(ownerId, status)
                .stream()
                .map(BookingMapper::toBookingDto)
                .toList();
    }

    private void checkStatus(String status) {
        try {
            Enum.valueOf(Statuses.class, status);
        } catch (IllegalArgumentException e) {
            throw new ParameterNotValidException("Некорректный статус: " + status);
        }
    }
}
