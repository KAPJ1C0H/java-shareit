package ru.practicum.shareit.item.comment;

import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.dto.CommentInfoDto;
import ru.practicum.shareit.item.dto.ItemDto;

public interface CommentService {
    CommentInfoDto create(long itemId, CommentDto text, long userId);

    ItemDto getItemInfo(long itemId);
}
