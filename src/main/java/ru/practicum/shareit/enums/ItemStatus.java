package ru.practicum.shareit.enums;

public enum ItemStatus {
    TRUE,
    FALSE;

    public static ItemStatus fromBoolean(boolean value) {
        return value ? TRUE : FALSE;
    }
}
