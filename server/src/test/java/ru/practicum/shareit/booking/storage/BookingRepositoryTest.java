package ru.practicum.shareit.booking.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BookingRepositoryTest {
    Booking booking;
    User user;
    Item item;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void beforeEach() {
        user = new User(
                null,
                "BookingRepositoryTest",
                "BookingRepositoryTest@example.com"
        );

        item = new Item(
                null,
                "BookingRepositoryTest",
                "BookingRepositoryTest description",
                true,
                user.getId(),
                null,
                null,
                null
        );
    }

    private Booking saveTestBooking() {
        User savedUser = userRepository.save(user);
        item.setOwnerId(savedUser.getId());
        Item savedItem = itemRepository.save(item);

        booking = new Booking(
                null,
                LocalDateTime.now().minusSeconds(10L),
                LocalDateTime.now().minusSeconds(1L),
                savedItem.getId(),
                savedUser.getId(),
                Statuses.APPROVED.name(),
                savedItem,
                savedUser
        );

        return bookingRepository.save(booking);
    }

    @Test
    @DisplayName("Сохранить бронирование")
    void saveBooking_Test() {
        Booking savedBooking = saveTestBooking();

        assertThat(savedBooking.getStart()).isEqualTo(booking.getStart());
        assertThat(savedBooking.getEnd()).isEqualTo(booking.getEnd());
        assertThat(savedBooking.getItemId()).isEqualTo(booking.getItemId());
        assertThat(savedBooking.getBookerId()).isEqualTo(booking.getBookerId());
        assertThat(savedBooking.getStatus()).isEqualTo(booking.getStatus());
    }

    @Test
    @DisplayName("Найти сущесвующее бронирование по id")
    void findBookingById_existing() {
        Booking savedBooking = saveTestBooking();

        Optional<Booking> foundBooking = bookingRepository.findBookingById(savedBooking.getId());

        assertThat(foundBooking).isPresent();
        assertThat(foundBooking.get().getStart()).isEqualTo(booking.getStart());
        assertThat(foundBooking.get().getEnd()).isEqualTo(booking.getEnd());
        assertThat(foundBooking.get().getItemId()).isEqualTo(booking.getItemId());
        assertThat(foundBooking.get().getBookerId()).isEqualTo(booking.getBookerId());
        assertThat(foundBooking.get().getStatus()).isEqualTo(booking.getStatus());
    }

    @Test
    @DisplayName("Найти несущесвующее бронирование по id")
    void findBookingById_notExisting() {
        Optional<Booking> foundBooking = bookingRepository.findBookingById(-1L);
        assertThat(foundBooking).isEmpty();
    }

    @Test
    @DisplayName("Найти существующее бронирование по существующему пользователю")
    void findBookingByBookerId_existingBooker() {
        Booking savedBooking = saveTestBooking();

        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingRepository.findBookingByBookerId(savedBooking.getBookerId());

        assertEquals(1, bookingList.size());
        assertThat(bookingList.getFirst().getStart()).isEqualTo(booking.getStart());
        assertThat(bookingList.getFirst().getEnd()).isEqualTo(booking.getEnd());
        assertThat(bookingList.getFirst().getItemId()).isEqualTo(booking.getItemId());
        assertThat(bookingList.getFirst().getBookerId()).isEqualTo(booking.getBookerId());
        assertThat(bookingList.getFirst().getStatus()).isEqualTo(booking.getStatus());
    }

    @Test
    @DisplayName("Найти бронирование по несуществующему пользователю")
    void findBookingByBookerId_nonExistingBooker() {
        saveTestBooking();

        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingRepository.findBookingByBookerId(-1L);

        assertEquals(0, bookingList.size());
    }

    @Test
    @DisplayName("Найти бронирования по существующему владельцу вещей")
    void findBookingListByOwnerId_existingOwner() {
        Booking savedBooking = saveTestBooking();

        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingRepository
                .findBookingListByOwnerId(savedBooking.getItem().getOwnerId());

        assertEquals(1, bookingList.size());
        assertThat(bookingList.getFirst().getStart()).isEqualTo(booking.getStart());
        assertThat(bookingList.getFirst().getEnd()).isEqualTo(booking.getEnd());
        assertThat(bookingList.getFirst().getItemId()).isEqualTo(booking.getItemId());
        assertThat(bookingList.getFirst().getBookerId()).isEqualTo(booking.getBookerId());
        assertThat(bookingList.getFirst().getStatus()).isEqualTo(booking.getStatus());
    }

    @Test
    @DisplayName("Найти бронирования по несуществующему владельцу вещей")
    void findBookingListByOwnerId_nonExistingOwner() {
        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingRepository
                .findBookingListByOwnerId(-1L);

        assertEquals(0, bookingList.size());
    }

    @Test
    @DisplayName("Сравнить предыдущее и следущее бронирования")
    void compareLastAndNextBookings() {
        Booking lastBooking = saveTestBooking();

        Booking nextBooking = new Booking(
                null,
                LocalDateTime.now().plusSeconds(1L),
                LocalDateTime.now().plusSeconds(10L),
                lastBooking.getItemId(),
                lastBooking.getBookerId(),
                Statuses.APPROVED.name(),
                lastBooking.getItem(),
                lastBooking.getBooker()
        );

        nextBooking = bookingRepository.save(nextBooking);

        assertEquals(
                lastBooking.getId(),
                bookingRepository.findFirstOneByItemIdAndStatusAndEndBeforeOrderByEndDesc(
                        lastBooking.getItemId(), lastBooking.getStatus(), LocalDateTime.now()
                ).get().getId()
        );
        assertEquals(
                nextBooking.getId(),
                bookingRepository.findFirstOneByItemIdAndStatusAndStartAfterOrderByStartAsc(
                        nextBooking.getItemId(), nextBooking.getStatus(), LocalDateTime.now()
                ).get().getId()
        );
    }

    @Test
    @DisplayName("Найти бронирования по существующим id вещи и id пользователя")
    void findByItemIdAndBookerId_existingItemAndBooker() {
        Booking savedBooking = saveTestBooking();

        Optional<Booking> foundBooking = bookingRepository
                .findByItemIdAndBookerId(savedBooking.getItemId(), savedBooking.getBookerId());

        assertTrue(foundBooking.isPresent());
        assertThat(foundBooking.get().getStart()).isEqualTo(savedBooking.getStart());
        assertThat(foundBooking.get().getEnd()).isEqualTo(savedBooking.getEnd());
        assertThat(foundBooking.get().getItemId()).isEqualTo(savedBooking.getItemId());
        assertThat(foundBooking.get().getBookerId()).isEqualTo(savedBooking.getBookerId());
        assertThat(foundBooking.get().getStatus()).isEqualTo(savedBooking.getStatus());
    }

    @Test
    @DisplayName("Найти бронирования с id несуществующей вещи и id существующего пользователя")
    void findByItemIdAndBookerId_nonExistingItem() {
        Booking savedBooking = saveTestBooking();

        Optional<Booking> foundBooking = bookingRepository
                .findByItemIdAndBookerId(-1L, savedBooking.getBookerId());
        assertTrue(foundBooking.isEmpty());
    }

    @Test
    @DisplayName("Найти бронирования с id существующей вещи и id несуществующего пользователя")
    void findByItemIdAndBookerId_nonExistingBooker() {
        Booking savedBooking = saveTestBooking();

        Optional<Booking> foundBooking = bookingRepository
                .findByItemIdAndBookerId(savedBooking.getItemId(), -1L);
        assertTrue(foundBooking.isEmpty());
    }
}

