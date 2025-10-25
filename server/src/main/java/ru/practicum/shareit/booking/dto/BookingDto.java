package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.enums.BookStatus;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class BookingDto {
    @NotNull
    @FutureOrPresent(message = "Дата начала должна быть в будущем или настоящем")
    private LocalDateTime start;

    @NotNull
    @Future(message = "Дата окончания должна быть в будущем")
    private LocalDateTime end;

    @NotNull
    private Long itemId;

    private Long booker;

    private BookStatus status;
}
