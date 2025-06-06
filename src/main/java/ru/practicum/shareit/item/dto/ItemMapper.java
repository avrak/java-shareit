package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

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
            Collection<Comment> comments,
            Booking lastBooking,
            Booking nextBooking
    ) {
        ItemWithDateTimeDto itemWithDateTimeDto = new ItemWithDateTimeDto();
        Collection<CommentDto> commentListDto = comments.stream().map(CommentMapper::toCommentDto).toList();

        itemWithDateTimeDto.setId(item.getId());
        itemWithDateTimeDto.setName(item.getName());
        itemWithDateTimeDto.setDescription(item.getDescription());
        itemWithDateTimeDto.setAvailable(item.getAvailable());
        itemWithDateTimeDto.setOwner(item.getOwner());
        itemWithDateTimeDto.setRequest(item.getRequest());
        itemWithDateTimeDto.setLastBooking(lastBooking.getId() == null ? null : BookingMapper.toBookingDto(lastBooking));
        itemWithDateTimeDto.setNextBooking(nextBooking.getId() == null ? null : BookingMapper.toBookingDto(nextBooking));
        itemWithDateTimeDto.setComments(commentListDto.isEmpty() ? null : commentListDto);

        return itemWithDateTimeDto;
    }
}
