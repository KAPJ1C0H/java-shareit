package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingStateGet;


@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> bookingAdd(@Validated @RequestBody BookItemRequestDto booking, @RequestHeader("X-Sharer-User-Id") long userId) {
		log.info("User ID: " + userId, "\n Item: " + booking.getItemId());
		return bookingClient.bookItem(userId, booking);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> bookingApproved(@PathVariable("bookingId") long id,
												  @RequestParam(value = "approved") boolean approve,
												  @RequestHeader("X-Sharer-User-Id") long userId) {
		return bookingClient.approved(id, approve, userId);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getBooking(@PathVariable("id") Long bookingId,
											 @RequestHeader("X-Sharer-User-Id") long userId) {
		return bookingClient.getBooking(userId, bookingId);
	}

	//Это его бронирования ЕГО ОНА САМ БРАНИРОВАЛ
	@GetMapping
	public ResponseEntity<Object> getAllBookingByUser(@RequestHeader("X-Sharer-User-Id") long userId,
													  @RequestParam(value = "state", defaultValue = "ALL")
													  BookingStateGet state) {
		return bookingClient.getAllBookingByUser(userId, state);
	}

	//Это вещи овнера
	@GetMapping("/owner")
	public ResponseEntity<Object> getAllBookingItemOwner(@RequestHeader("X-Sharer-User-Id") long userId,
														 @RequestParam(value = "state", defaultValue = "ALL")
														 BookingStateGet state) {
		return bookingClient.getAllBookingByOwner(userId, state);
	}
}