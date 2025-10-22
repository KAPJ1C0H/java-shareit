package ru.practicum.shareit.item.comment.exception;

public class CommentNotAllowedException extends RuntimeException {
    public CommentNotAllowedException(String massage) {
        super(massage);
    }
}
