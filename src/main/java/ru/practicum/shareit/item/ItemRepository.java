package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {

    Collection<Item> getAll();

    Collection<Item> getAllByOwnerId(long ownerId);

    Item save(Item item);

    Item update(Item item);

    boolean delete(long id);

    Item getById(long id);

    Collection<Item> search(String text);
}
