package ru.practicum.shareit.booking.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.enums.BookStatus;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class BookingMapper {
    @Autowired
    ItemRepository itemRepository;

    public static BookingInfoDto toBookingInfoDto(Booking booking) {
        return BookingInfoDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(Item.builder()
                        .id(booking.getItem().getId())
                        .name(booking.getItem().getName())
                        .build())
                .booker(User.builder()
                        .id(booking.getBooker().getId())
                        .build())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toBooking(BookingDto bookingDto, User booker, Item item) {
        return Booking.builder()
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .booker(booker)
                .item(item)
                .status(bookingDto.getStatus() == null ? BookStatus.WAITING : bookingDto.getStatus())
                .build();
    }
}