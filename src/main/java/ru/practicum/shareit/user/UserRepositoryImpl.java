package ru.practicum.shareit.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.user.exception.OnValidEmail;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Collection<User> users = new HashSet<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Collection<User> getAll() {
        return users;
    }

    @Override
    public User save(User user) {
        if (!users.stream()
                .filter(user1 -> user1.getEmail().equals(user.getEmail()))
                .toList().isEmpty()) {
            throw new OnValidEmail("email уже зарегестрирован");
        }
        user.setId(idGenerator.incrementAndGet());
        users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getEmail() != null && !users.stream()
                .filter(user1 -> user1.getEmail() != null && user1.getEmail().equals(user.getEmail()))
                .filter(user1 -> user1.getId() != user.getId())
                .toList().isEmpty()) {
            throw new OnValidEmail("email уже зарегестрирован");
        }
        delete(user.getId());
        users.add(user);
        return getById(user.getId());
    }

    @Override
    public boolean delete(long id) {
        return users.removeIf(user -> user.getId() == id);
    }

    @Override
    public User getById(long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }
}
