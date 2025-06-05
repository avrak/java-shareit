package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setOwner(item.getOwner());
        itemDto.setRequest(item.getRequest());

        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();

        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(itemDto.getOwner());
        item.setRequest(itemDto.getRequest());

        return item;
    }

    public static ItemWithDateTimeDto toItemWithDatesDto(
            Item item,
            LocalDateTime pastStart,
            LocalDateTime pastEnd,
            LocalDateTime nextStart,
            LocalDateTime nextEnd
    ) {
        ItemWithDateTimeDto itemWithDateTimeDto = new ItemWithDateTimeDto();

        itemWithDateTimeDto.setId(item.getId());
        itemWithDateTimeDto.setName(item.getName());
        itemWithDateTimeDto.setDescription(item.getDescription());
        itemWithDateTimeDto.setAvailable(item.getAvailable());
        itemWithDateTimeDto.setOwner(item.getOwner());
        itemWithDateTimeDto.setRequest(item.getRequest());
        itemWithDateTimeDto.setLastBookingStart(pastStart);
        itemWithDateTimeDto.setLastBookingEnd(pastEnd);
        itemWithDateTimeDto.setNextBookingStart(nextStart);
        itemWithDateTimeDto.setNextBookingEnd(nextEnd);

        return itemWithDateTimeDto;
    }

    public static ItemWithCommentsDto toItemWithCommentsDto(Item item, Collection<Comment> comments) {
        ItemWithCommentsDto itemWithCommentsDto = new ItemWithCommentsDto();

        itemWithCommentsDto.setId(item.getId());
        itemWithCommentsDto.setName(item.getName());
        itemWithCommentsDto.setDescription(item.getDescription());
        itemWithCommentsDto.setAvailable(item.getAvailable());
        itemWithCommentsDto.setOwner(item.getOwner());
        itemWithCommentsDto.setRequest(item.getRequest());

        Collection<CommentDto> commentListDto = new ArrayList<>();
        commentListDto = comments.stream().map(CommentMapper::toCommentDto).toList();

        itemWithCommentsDto.setComments(commentListDto);

        return itemWithCommentsDto;

    }
}
