package ru.practicum.shareit.item.exception;

public class PermissionError extends RuntimeException {
    public PermissionError(String e) {
        super(e);
    }
}
