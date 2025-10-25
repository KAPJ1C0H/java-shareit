package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserClient userService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        return userService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Object> add(@Validated @RequestBody User user) {
        return userService.save(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @Validated @RequestBody UserUpdateDto userUpdateDto) {
        return userService.update(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userService.delete(id);
    }
}

