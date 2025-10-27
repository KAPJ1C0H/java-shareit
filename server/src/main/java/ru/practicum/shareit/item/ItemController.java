package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.CommentService;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.dto.CommentInfoDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Collection;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ItemService itemService;

    @GetMapping
    public Collection<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getAll(userId);
    }

//    @GetMapping("/{id}")
//    public ItemDto getById(@PathVariable("id") long id) {
//        return itemService.getById(id);
//    }

    @PostMapping("/{itemId}/comment")
    public CommentInfoDto addComment(@PathVariable("itemId") long itemId,
                                     @RequestBody CommentDto text,
                                     @RequestHeader("X-Sharer-User-Id") long userId) {
        return commentService.create(itemId, text, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getCommentsForItem(@PathVariable("itemId") long itemId) {
        return commentService.getItemInfo(itemId);
    }

    @PostMapping
    public ItemDto save(@RequestHeader("X-Sharer-User-Id") long userId,
                        @Validated @RequestBody ItemDto itemDto) {
        return itemService.save(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable("id") long id,
                          @Validated @RequestBody ItemUpdateDto itemDto) {

        return itemService.update(userId, id, itemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        itemService.delete(id);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam("text") String text) {
        return itemService.search(text);
    }
}
