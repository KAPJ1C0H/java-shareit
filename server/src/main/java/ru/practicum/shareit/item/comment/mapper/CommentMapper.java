package ru.practicum.shareit.item.comment.mapper;

import ru.practicum.shareit.item.comment.dto.CommentInfoDto;
import ru.practicum.shareit.item.comment.model.Comment;

public class CommentMapper {
    public static CommentInfoDto toDto(Comment comment) {
        return CommentInfoDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getUser().getName())
                .created(comment.getCreateDataTime())
                .build();
    }
}
