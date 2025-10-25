package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingInfoDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.exception.InvalidFiledBooking;
import ru.practicum.shareit.booking.exception.ItemIsAlreadyOnLease;
import ru.practicum.shareit.enums.BookStatus;
import ru.practicum.shareit.enums.BookingStateGet;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.exception.ItemNotFound;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public BookingInfoDto add(BookingDto bookingDto, long userId) {
        User booker = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new ItemNotFound("Item not found"));
        Optional<Booking> inMamBooking = bookingRepository.findTopByItemIdOrderByIdDesc(bookingDto.getItemId());
        if (!item.getAvailable()) {
            throw new ItemIsAlreadyOnLease("Item in lease");
        }
        if (item.getOwnerId() == userId) {
            throw new RuntimeException("Owner can't rent his item");
        }
//        if (bookingDto.getEnd().isBefore(LocalDateTime.now())) {
//            throw new InvalidFiledBooking("incorrect end or start time");
//        }
//        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Start time cannot be in the past");
//        }
        if (bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new RuntimeException("End time cannot be earlier than start time");
        }
        if (bookingDto.getEnd().equals(bookingDto.getStart())) {
            throw new InvalidFiledBooking("End time can't start time :(");
        }
        item.setAvailable(true);
        Booking booking = BookingMapper.toBooking(bookingDto, booker, item);
        booking = bookingRepository.save(booking);
        return BookingMapper.toBookingInfoDto(booking);
    }


    public BookingInfoDto update(Booking booking) {
        booking.setStatus(BookStatus.WAITING);
        return BookingMapper.toBookingInfoDto(bookingRepository.save(booking));
    }

    public BookingInfoDto approved(long bookingId, boolean approve, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getItem().getOwnerId() != userId) {
            throw new RuntimeException("In valid permission");
        }
        booking.setStatus(approve ? BookStatus.APPROVED : BookStatus.REJECTED);
        if (booking.getStatus().equals(BookStatus.APPROVED)) {
            booking.getItem().setAvailable(false);
        }
        return BookingMapper.toBookingInfoDto(bookingRepository.save(booking));
    }

    public BookingInfoDto getBooking(long id, Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (booking.getBooker().getId() != userId && booking.getItem().getOwnerId() != userId) {
            throw new RuntimeException("In valid permission");
        }
        return BookingMapper.toBookingInfoDto(booking);
    }

    public Collection<BookingInfoDto> getAllBookingByOwnre(long userId, BookingStateGet state) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        switch (state) {
            case ALL:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId).stream()
                        .map(BookingMapper::toBookingInfoDto).collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId).stream()
                        .filter(booking -> booking.getEnd().isAfter(LocalDateTime.from(Instant.now())))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId).stream()
                        .filter(booking -> booking.getEnd().isBefore(LocalDateTime.from(Instant.now())))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId).stream()
                        .filter(booking -> booking.getStart().isAfter(LocalDateTime.from(Instant.now())))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId).stream()
                        .filter(booking -> booking.getStatus().equals(BookStatus.WAITING))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId).stream()
                        .filter(booking -> booking.getStatus().equals(BookStatus.REJECTED))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("In correct state");
        }
    }

    public Collection<BookingInfoDto> getAllBookingByUser(long userId, BookingStateGet state) {
        switch (state) {
            case ALL:
                return bookingRepository.findAllByBookerId(userId).stream()
                        .map(BookingMapper::toBookingInfoDto).collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findAllByBookerId(userId).stream()
                        .filter(booking -> booking.getEnd().isAfter(LocalDateTime.from(Instant.now())))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findAllByBookerId(userId).stream()
                        .filter(booking -> booking.getEnd().isBefore(LocalDateTime.from(Instant.now())))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findAllByBookerId(userId).stream()
                        .filter(booking -> booking.getStart().isAfter(LocalDateTime.from(Instant.now())))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findAllByBookerId(userId).stream()
                        .filter(booking -> booking.getStatus().equals(BookStatus.WAITING))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findAllByBookerId(userId).stream()
                        .filter(booking -> booking.getStatus().equals(BookStatus.REJECTED))
                        .map(BookingMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("In correct state");
        }
    }
}
