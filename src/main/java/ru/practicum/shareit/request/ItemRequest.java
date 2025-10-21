package ru.practicum.shareit.request;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder(toBuilder = true)
public class ItemRequest {
    private long id;
    private long ownerId;
    private String nameItem;
    private String descriptionItem;
    private Collection<Long> offersItemsId;
}
