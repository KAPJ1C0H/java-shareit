package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder(toBuilder = true)
public class ItemRequestDto {
    private long id;
    private long ownerId;
    private String nameItem;
    private String descriptionItem;
    private Collection<Long> offersItemsId;
}

