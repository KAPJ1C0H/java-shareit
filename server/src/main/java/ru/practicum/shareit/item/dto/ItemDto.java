package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingInfoDto;
import ru.practicum.shareit.item.comment.dto.CommentInfoDto;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ItemDto {
    private long id;
    private long ownerId;
    private int beenOnLoan;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private List<CommentInfoDto> comments;
    private BookingInfoDto lastBooking;
    private BookingInfoDto nextBooking;
    private Long requestId;
}
