package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final RequestService requestService;

    @PostMapping
    public ItemRequestDto addRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                     @Validated @RequestBody ItemRequestDto itemRequestDto) {
        return requestService.create(userId, itemRequestDto);
    }

    @GetMapping
    public List<ItemRequestDto> getRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        return requestService.getAllRequestsForUser(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        return requestService.getAllItemRequests(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestDto getRequest(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable("id") long id) {
        return requestService.getItemRequest(userId, id);
    }
}
