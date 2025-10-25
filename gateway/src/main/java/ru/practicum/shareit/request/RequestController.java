package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    @Autowired
    private final RequestClient requestService;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @Validated @RequestBody ItemRequestDto itemRequestDto) {
        return requestService.create(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        return requestService.getAllRequestsForUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        return requestService.getAllItemRequests(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequest(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable("id") long id) {
        return requestService.getItemRequest(userId, id);
    }
}
