package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.exception.ValidationException;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional
    public ItemRequestDto create(long userId, ItemRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found" + userId));
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(requestDto, user);
        if (itemRequest.getDescription() == null || itemRequest.getDescription().isBlank()) {
            throw new ValidationException("Description is required");
        }
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toItemRequestDto(requestRepository.save(itemRequest), null);
    }

    @Override
    public List<ItemRequestDto> getAllRequestsForUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found" + userId));
        List<ItemRequest> itemRequests = requestRepository.findAllByRequesterIdOrderByCreatedDesc(userId);
        if (itemRequests.isEmpty()) {
            return List.of();
        }
        List<ItemRequestDto> itemRequestDtos = itemRequests.stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());

        List<Long> requestIdList = itemRequestDtos.stream()
                .map(ItemRequestDto::getId)
                .collect(Collectors.toList());

        List<Item> items = itemRepository.findAllById(requestIdList);

        for (ItemRequestDto itemRequestDto : itemRequestDtos) {
            List<Item> requestItems = items.stream()
                    .filter(item -> item.getRequest() != null)
                    .filter(item -> item.getRequest().getId() == itemRequestDto.getId())
                    .toList();
            if (!requestItems.isEmpty()) {
                List<ItemDto> itemDtos = requestItems.stream()
                        .map(ItemMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
                itemRequestDto.setItems(itemDtos);
            }
        }
        return itemRequestDtos;
    }

    // Список запросов созданных другими пользователями
    // от наиболее новых к наиболее старым
    @Override
    public List<ItemRequestDto> getAllItemRequests(long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        List<ItemRequest> itemRequests = requestRepository.findAllByRequesterIdIsNot(userId);

        List<ItemRequestDto> itemRequestDtos = itemRequests.stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());

        List<Long> requestIdList = itemRequestDtos.stream()
                .map(ItemRequestDto::getId)
                .collect(Collectors.toList());
        List<Item> items = itemRepository.findAllByRequestIdIn(requestIdList);

        for (ItemRequestDto itemRequestDto : itemRequestDtos) {
            List<Item> requestItems = items.stream()
                    .filter(item -> item.getRequest().getId() == itemRequestDto.getId())
                    .collect(Collectors.toList());
            if (!requestItems.isEmpty()) {
                List<ItemDto> itemDtos = requestItems.stream()
                        .map(ItemMapper::toBookingInfoDto)
                        .collect(Collectors.toList());
                itemRequestDto.setItems(itemDtos);
            }
        }
        return itemRequestDtos;
    }

    @Override
    public ItemRequestDto getItemRequest(long userId, long requestId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        ItemRequest itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new UserNotFoundException("request not found"));

        List<Item> items = itemRepository.findAllByRequest(requestId);
        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest, null);

        if (!items.isEmpty()) {
            List<ItemDto> itemDtos = items.stream()
                    .map(ItemMapper::toBookingInfoDto)
                    .toList();
            itemRequestDto.setItems(itemDtos);
        }
        return itemRequestDto;
    }
}
