package ru.practicum.shareit.user.exception;

public class OnValidEmail extends RuntimeException {
    public OnValidEmail(String e) {
        super(e);
    }
}
