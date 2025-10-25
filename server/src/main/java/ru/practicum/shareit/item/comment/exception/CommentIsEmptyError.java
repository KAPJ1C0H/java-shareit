package ru.practicum.shareit.item.comment.exception;

public class CommentIsEmptyError extends RuntimeException {
    public CommentIsEmptyError(String msg) {
        super(msg);
    }
}
