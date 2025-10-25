package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.Collection;

public interface UserService {

    Collection<User> getAll();

    User save(User user);

    User update(UserUpdateDto user);

    void delete(long id);

    User getById(long id);
}
