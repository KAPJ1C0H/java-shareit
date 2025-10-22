package ru.practicum.shareit.user.exception;

public class ParamValidationError extends RuntimeException {
    public ParamValidationError(String e) {
        super(e);
    }
}
