package ru.practicum.shareit.item.comment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class CommentInfoDto {
    private long id;

    private String text;

    private String authorName;

    private LocalDateTime created;
}
