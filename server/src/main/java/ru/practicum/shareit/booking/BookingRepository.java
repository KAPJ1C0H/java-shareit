package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.enums.BookStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerId(long id);

    List<Booking> findAllByBookerIdAndItemId(long userId, long itemId);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(long userId);

    Optional<Booking> findTopByItemIdOrderByIdDesc(long id);

    Optional<Booking> findTop1ByItemIdAndStatusAndStartAfterOrderByStartAsc(long id, BookStatus status,
                                                                            LocalDateTime now);

    Optional<Booking> findTop1ByItemIdAndStatusAndEndIsBeforeOrderByEndDesc(
            long itemId,
            BookStatus status,
            LocalDateTime now
    );
}
