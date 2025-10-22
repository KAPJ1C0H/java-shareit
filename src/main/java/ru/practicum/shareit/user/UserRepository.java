package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserRepository {

    Collection<User> getAll();

    User save(User user);

    User update(User user);

    boolean delete(long id);

    User getById(long id);
}
