package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingInfoDto;
import ru.practicum.shareit.enums.BookingStateGet;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingInfoDto bookingAdd(@Validated @RequestBody BookingDto booking, @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info(String.valueOf(booking));
        return bookingService.add(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingInfoDto bookingApproved(@PathVariable("bookingId") long id,
                                          @RequestParam(value = "approved") boolean approve,
                                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.approved(id, approve, userId);
    }

    @GetMapping("/{id}")
    public BookingInfoDto getBooking(@PathVariable("id") long id,
                                     @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getBooking(id, userId);
    }

    //Это его бронирования ЕГО ОНА САМ БРАНИРОВАЛ
    @GetMapping
    public Collection<BookingInfoDto> getAllBookingByUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                          @RequestParam(value = "state", defaultValue = "ALL")
                                                          BookingStateGet state) {
        return bookingService.getAllBookingByUser(userId, state);
    }

    //Это вещи овнера
    @GetMapping("/owner")
    public Collection<BookingInfoDto> getAllBookingItemOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                             @RequestParam(value = "state", defaultValue = "ALL")
                                                             BookingStateGet state) {
        return bookingService.getAllBookingByOwnre(userId, state);
    }
}
