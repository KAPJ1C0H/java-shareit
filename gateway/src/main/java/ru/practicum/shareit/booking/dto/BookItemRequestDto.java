package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {
    @NotNull
    @FutureOrPresent(message = "Дата начала должна быть в будущем или настоящем")
	private LocalDateTime start;

    @NotNull
    @Future(message = "Дата окончания должна быть в будущем")
	private LocalDateTime end;

    @NotNull
    private Long itemId;

    private Long booker;

    private BookingState status;
}
