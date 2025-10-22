package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final UserRepository userRepository;
    private final AtomicLong idGenerator = new AtomicLong(0);

    private final Collection<Item> items = new HashSet<>();

    @Override
    public Collection<Item> getAll() {
        return items;
    }

    @Override
    public Collection<Item> getAllByOwnerId(long ownerId) {
        return items.stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .collect(Collectors.toList());
    }


    @Override
    public Item save(Item item) {
        userRepository.getById(item.getOwnerId());
        item.setId(idGenerator.incrementAndGet());
        items.add(item);
        return getById(item.getId());
    }

    @Override
    public Item update(Item item) {
        delete(item.getId());
        items.add(item);
        return getById(item.getId());
    }

    @Override
    public boolean delete(long id) {
        return items.removeIf(item -> item.getId() == id);
    }

    @Override
    public Item getById(long id) {
        return items.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Collection<Item> search(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        return items.stream()
                .filter(item -> (item.getName() != null && item.getName().toLowerCase().contains(text.toLowerCase()))
                        || (item.getDescription() != null && item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }


}
