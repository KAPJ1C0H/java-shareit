package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.exception.ItemNotFound;
import ru.practicum.shareit.item.exception.PermissionError;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    public Collection<ItemDto> getAll(long userId) {
        return itemRepository.findByOwnerId(userId).stream()
                .map(ItemMapper::toBookingInfoDto)
                .collect(Collectors.toSet());
    }

    public ItemDto getById(long id) {
        return ItemMapper.toBookingInfoDto(itemRepository.getById(id));
    }

    public ItemDto save(long userId, ItemDto itemDto) {
        if (userRepository.findById(userId).isEmpty()) throw new ItemNotFound("ItemNot Found");

        Item item = ItemMapper.fromDto(itemDto).toBuilder().ownerId(userId).build();
        return ItemMapper.toBookingInfoDto(itemRepository.save(item));
    }

    public ItemDto update(long userId,
                          long id,
                          ItemUpdateDto itemUpdateDto) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found")); // Обработка, если item не найден

        if (existingItem.getOwnerId() != userId) {
            throw new PermissionError("Only owner can update item");
        }

        Item.ItemBuilder itemBuilder = existingItem.toBuilder();

        if (itemUpdateDto.getName() != null && !itemUpdateDto.getName().isBlank()) {
            itemBuilder.name(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null && !itemUpdateDto.getDescription().isBlank()) {
            itemBuilder.description(itemUpdateDto.getDescription());
        }
        if (itemUpdateDto.getAvailable() != null) {
            itemBuilder.available(itemUpdateDto.getAvailable());
        }

        Item updatedItem = itemRepository.save(itemBuilder.build());

        return ItemMapper.toBookingInfoDto(updatedItem);
    }


    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    public Collection<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return itemRepository.searchAvailableByText(text).stream()
                .filter(Item::getAvailable) // Сравнение с ItemStatus.TRUE
                .map(ItemMapper::toBookingInfoDto)
                .collect(Collectors.toList());
    }
}
