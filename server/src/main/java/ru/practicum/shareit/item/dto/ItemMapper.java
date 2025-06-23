package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestMapper;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setOwnerId(item.getOwnerId());
        itemDto.setRequestId(item.getRequestId());
        itemDto.setRequest(item.getRequest() != null ? ItemRequestMapper.toRequestDto(item.getRequest()) : null);

        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();

        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwnerId(itemDto.getOwnerId());
        item.setRequestId(itemDto.getRequestId());
        item.setRequest(itemDto.getRequest() != null ? ItemRequestMapper.toRequest(itemDto.getRequest()) : null);

        return item;
    }

    public static ItemWideDto toItemWideDto(
            Item item,
            Booking lastBooking,
            Booking nextBooking
    ) {
        ItemWideDto itemWideDto = new ItemWideDto();

        itemWideDto.setId(item.getId());
        itemWideDto.setName(item.getName());
        itemWideDto.setDescription(item.getDescription());
        itemWideDto.setAvailable(item.getAvailable());
        itemWideDto.setOwner(item.getOwnerId());
        itemWideDto.setRequest(item.getRequestId());
        itemWideDto.setLastBooking(lastBooking.getId() == null ? null : BookingMapper.toBookingDto(lastBooking));
        itemWideDto.setNextBooking(nextBooking.getId() == null ? null : BookingMapper.toBookingDto(nextBooking));
        itemWideDto.setComments(
                item.getComments().isEmpty()
                        ? new ArrayList<>()
                        : item.getComments().stream().map(CommentMapper::toCommentDto).toList()
        );

        return itemWideDto;
    }

    public static ItemShortDto toItemShortDto(Item item) {
        ItemShortDto itemShortDto = new ItemShortDto();

        itemShortDto.setId(item.getId());
        itemShortDto.setName(item.getName());
        itemShortDto.setDescription(item.getDescription());
        itemShortDto.setAvailable(item.getAvailable());
        itemShortDto.setOwnerId(item.getOwnerId());

        return itemShortDto;
    }
}
