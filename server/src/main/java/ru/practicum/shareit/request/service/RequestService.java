package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.dto.ItemRequestDto;

import java.util.List;

public interface RequestService {
    ItemRequestDto create(long userId, ItemRequestDto requestDto);

    List<ItemRequestDto> getAllItemRequests(long userId);

    ItemRequestDto getItemRequest(long userId, long requestId);

    List<ItemRequestDto> getAllRequestsForUser(long userId);

}
