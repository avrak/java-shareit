package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {

    Optional<Booking> findBookingById(Long bookingId);

    Collection<Booking> findBookingByBookerId(Long bookerId);

    @Query("""
        select b
          from Item i
          join Booking b on b.itemId = i.id
         where i.owner = ?1
           and b.status = 'APPROVED'
         order by b.end desc
        """)
    Collection<Booking> findBookingListByOwnerAndStatus(Long ownerId, String status);


    Optional<Booking> findFirstOneByItemIdAndStatusAndEndBeforeOrderByEndDesc(Long itemId, String status,
                                                                              LocalDateTime end);

    Optional<Booking> findFirstOneByItemIdAndStatusAndStartAfterOrderByStartAsc(Long itemId, String status,
                                                                                LocalDateTime end);

    Optional<Booking> findByItemIdAndBookerId(Long itemId, Long bookerId);
}
