package ru.practicum.shareit.booking.exception;

public class ItemIsAlreadyOnLease extends RuntimeException {
    public ItemIsAlreadyOnLease(String m) {
        super(m);
    }
}
