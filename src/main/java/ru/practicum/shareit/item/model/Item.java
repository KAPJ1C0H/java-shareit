package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Item {
    private Long id;
    private long ownerId;
    private int beenOnLoan;
    private String name;
    private String description;
    private Boolean available;
}
