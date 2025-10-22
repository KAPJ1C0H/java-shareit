package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static ItemDto toBookingInfoDto(Item item) {
        return ItemDto.builder()
                .ownerId(item.getOwnerId())
                .id(item.getId())
                .beenOnLoan(item.getBeenOnLoan())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static Item fromUpdateDto(ItemUpdateDto itemDto) {
        return Item.builder()
                .beenOnLoan(itemDto.getBeenOnLoan())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public static Item fromDto(ItemDto itemDto) {
        return Item.builder()
                .beenOnLoan(itemDto.getBeenOnLoan())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }
}
