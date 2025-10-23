package ru.practicum.shareit.item.exception;

public class ItemNotFound extends RuntimeException {
    public ItemNotFound(String msg) {
        super(msg);
    }
}
