package ru.practicum.shareit.item.comment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class CommentErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> commentIsEmpty(CommentIsEmptyError e) {
        return Map.of("Comment is in valid: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badHuman(CommentNotAllowedException e) {
        return Map.of("You did not rent this item", e.getMessage());
    }
}
