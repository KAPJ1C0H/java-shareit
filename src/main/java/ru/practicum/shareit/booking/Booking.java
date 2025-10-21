package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.enums.BookStatus;

import java.time.LocalDateTime;

@Data
public class Booking {
    private long id;
    private long bookUser;
    private long ownerId;
    private long itemId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookStatus status;
}
