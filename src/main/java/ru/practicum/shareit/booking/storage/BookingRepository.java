package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
//    public Booking saveBooking(Booking booking);

    public Optional<Booking> findBookingById(Long bookingId);

    public Collection<Booking> findBookingByBookerAndStatus(Long bookerId, String status);

    @Query("""
        select b
          from Item i
          join Booking b on b.item = i.id
         where i.owner = ?1
           and b.status = ?2
         order by b.end desc
        """)
    public Collection<Booking> findBookingListByOwnerAndStatus(Long ownerId, String status);

    @Query("""
        select b
          from Booking b
         where b.item = ?1
           and b.status = 'APPROVED'
         order by b.end desc
        """)
    Collection<Booking> findBookingsPast(Long itemId);

    default Optional<Booking> getLastEndedBooking(Long itemId) {
        return findBookingsPast(itemId).stream().findFirst();
    };

    @Query("""
            select b
              from Booking b
             where b.item = ?1
               and b.status = 'FUTURE'
              order by b.end asc
            """
    )
    List<Booking> findBookingsNext(Long itemId);

    default Optional<Booking> getNextBooking(Long itemId) {
        return findBookingsNext(itemId).stream().findFirst();
    };
}
