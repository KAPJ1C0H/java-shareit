package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

@AllArgsConstructor
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
                .request(itemDto.getRequestId() == null ?
                        null : ItemRequest.builder()
                        .id(itemDto.getRequestId())
                        .build())
                .build();
    }
}
