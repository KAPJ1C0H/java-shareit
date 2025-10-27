package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.util.HttpHeaders;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestClient requestService;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader(HttpHeaders.USER_ID_HEADER) long userId,
                                             @Validated @RequestBody ItemRequestDto itemRequestDto) {
        return requestService.create(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader(HttpHeaders.USER_ID_HEADER) long userId) {
        return requestService.getAllRequestsForUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(HttpHeaders.USER_ID_HEADER) long userId) {
        return requestService.getAllItemRequests(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequest(@RequestHeader(HttpHeaders.USER_ID_HEADER) long userId,
                                             @PathVariable("id") long id) {
        return requestService.getItemRequest(userId, id);
    }
}