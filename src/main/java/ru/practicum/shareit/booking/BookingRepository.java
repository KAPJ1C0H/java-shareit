package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.enums.BookStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findById(long id);

    List<Booking> findAllByBookerId(long id);

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
