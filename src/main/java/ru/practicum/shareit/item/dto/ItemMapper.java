package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
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

    public static ItemWideDto toItemWideDto(
            Item item,
            Collection<Comment> comments,
            Booking lastBooking,
            Booking nextBooking
    ) {
        ItemWideDto itemWideDto = new ItemWideDto();
        Collection<CommentDto> commentListDto = comments.stream().map(CommentMapper::toCommentDto).toList();

        itemWideDto.setId(item.getId());
        itemWideDto.setName(item.getName());
        itemWideDto.setDescription(item.getDescription());
        itemWideDto.setAvailable(item.getAvailable());
        itemWideDto.setOwner(item.getOwner());
        itemWideDto.setRequest(item.getRequest());
        itemWideDto.setNow(LocalDateTime.now());
        itemWideDto.setLastBooking(lastBooking.getId() == null ? null : BookingMapper.toBookingDto(lastBooking));
        itemWideDto.setNextBooking(nextBooking.getId() == null ? null : BookingMapper.toBookingDto(nextBooking));
        itemWideDto.setComments(commentListDto.isEmpty() ? null : commentListDto);

        return itemWideDto;
    }
}
