package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getAll() {
        return userService.getAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") long id) {
        return UserMapper.toDto(userService.getById(id));
    }

    @PostMapping
    public UserDto add(@Valid @RequestBody User user) {
        return UserMapper.toDto(userService.save(user));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable("id") long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userUpdateDto.setId(id);
        return UserMapper.toDto(userService.update(userUpdateDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userService.delete(id);
    }
}
