package ru.practicum.shareit.booking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingObjDto;
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
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public BookingObjDto saveBooking(Long userId, BookingDto bookingDto) {

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

        bookingDto.setBooker(userId);
        bookingDto.setStatus(Statuses.WAITING);
        return BookingMapper.toBookingObjDto(
                bookingRepository.save(BookingMapper.toBooking(bookingDto)),
                ItemMapper.toItemDto(item),
                UserMapper.toUserDto(booker)
        );
    }

    @Override
    public BookingObjDto approveBookingById(Long userId, Long bookingId, Boolean status) {
        Booking booking = bookingRepository
                .findBookingById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с id=" + bookingId + " не найдено"));

        Item item = itemRepository
                .findItemById(booking.getItem())
                .orElseThrow(() -> new NotFoundException("Вещь с id=" + booking.getItem() + " не найдена"));

        if (!item.getOwner().equals(userId)) {
            throw new ForbiddenException("Только владелец вещи может подтвердить бронирование");
        }

        booking.setStatus(status ? Statuses.APPROVED.name() : Statuses.REJECTED.name());
        bookingRepository.save(booking);

        return BookingMapper.toBookingObjDto(
                booking,
                ItemMapper.toItemDto(item),
                UserMapper.toUserDto(userRepository.findUserById(booking.getBooker()).get())
        );
    }

    @Override
    public BookingObjDto getBookingById(Long userId, Long bookingId) {
        Booking booking = bookingRepository
                .findBookingById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование с id=" + bookingId + " не найдено"));


        Item item = itemRepository
                .findItemById(booking.getItem())
                .orElseThrow(() -> new NotFoundException("Вещь для бронирования не найдена"));

        if (!(userId.equals(booking.getBooker()) || userId.equals(item.getOwner()))) {
            throw new ForbiddenException("Только владелец вещи или арендатор могут просматривать бронирование!");
        }

        User booker = userRepository.findUserById(booking.getBooker()).get();

        return BookingMapper.toBookingObjDto(
                booking,
                ItemMapper.toItemDto(item),
                UserMapper.toUserDto(booker)
        );
    }

    @Override
    public Collection<BookingObjDto> getBookingListByBookerId(Long bookerId) {
        ArrayList<BookingObjDto> bookingDtoList = new ArrayList<>();

        for (Booking booking : bookingRepository.findBookingByBooker(bookerId)) {
            Item item = itemRepository.findItemById(booking.getItem()).get();
            User booker = userRepository.findUserById(bookerId).get();
            bookingDtoList.add(BookingMapper.toBookingObjDto(
                    booking,
                    ItemMapper.toItemDto(item),
                    UserMapper.toUserDto(booker))
            );
        }

        return bookingDtoList;
    }

    @Override
    public Collection<BookingObjDto> getBookingListByOwnerIdAndStatus(Long ownerId, String status) {
        checkStatus(status);

        ArrayList<BookingObjDto> bookingDtoList = new ArrayList<>();

        for (Booking booking : bookingRepository.findBookingListByOwnerAndStatus(ownerId, status)) {
            Item item = itemRepository.findItemById(booking.getItem()).get();
            User booker = userRepository.findUserById(booking.getBooker()).get();
            bookingDtoList.add(BookingMapper.toBookingObjDto(
                    booking,
                    ItemMapper.toItemDto(item),
                    UserMapper.toUserDto(booker))
            );
        }

        return bookingDtoList;
    }

    private void checkStatus(String status) {
        try {
            Enum.valueOf(Statuses.class, status);
        } catch (IllegalArgumentException e) {
            throw new ParameterNotValidException("Некорректный статус: " + status);
        }
    }
}
