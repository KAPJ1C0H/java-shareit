package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long id;

    private long ownerId;
    private int beenOnLoan;
    private String name;
    private String description;
    private Boolean available;
    private ItemRequest request;
}
