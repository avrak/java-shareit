package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.model.ForbiddenException;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDateTimeDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemService;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ItemWithDateTimeDto getItemById(Long itemId) {
        Item item = itemRepository
                .findItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id=" + itemId + " не найдена"));

        return ItemMapper.toItemWithDatesDto(
                item,
                commentRepository.findCommentsByItemId(itemId),
                bookingRepository.getLastEndedBooking(item.getId()).orElse(new Booking()),
                bookingRepository.getNextBooking(item.getId()).orElse(new Booking())
        );
    }

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        itemDto.setOwner(userRepository
                .findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + " не найден"))
                .getId()
        );

        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(itemDto)));
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        ItemDto currentItemDto = ItemMapper.toItemDto(
                itemRepository.findItemById(itemId).orElseThrow(
                        () -> new NotFoundException("Вещь с id=" + itemId + " не найдена")
                )
        );

        if (!userId.equals(currentItemDto.getOwner())) {
            throw new ForbiddenException("Только владелец может обновить вещь!");
        }

        if (itemDto.getName() != null) {
            currentItemDto.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            currentItemDto.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            currentItemDto.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(currentItemDto)));
    }

    @Override
    public Collection<ItemWithDateTimeDto> getItemListByOwner(Long ownerId) {
        Collection<ItemWithDateTimeDto> itemWithDateTimeDtoList = new ArrayList<>();

        for (Item item : itemRepository.findItemListByOwner(ownerId)) {
            itemWithDateTimeDtoList.add(ItemMapper.toItemWithDatesDto(
                    item,
                    commentRepository.findCommentsByItemId(item.getId()),
                    bookingRepository.getLastEndedBooking(item.getId()).orElse(new Booking()),
                    bookingRepository.getNextBooking(item.getId()).orElse(new Booking())
                    )
            );
        }

        return itemWithDateTimeDtoList;
    }

    @Override
    public Collection<ItemWithDateTimeDto> getItemListByText(String text) {
        Collection<ItemWithDateTimeDto> itemWithDateTimeDtoList = new ArrayList<>();

        if (text.isEmpty()) {
            return itemWithDateTimeDtoList;
        }

        for (Item item : itemRepository.findItemListByText(text)) {

            itemWithDateTimeDtoList.add(ItemMapper.toItemWithDatesDto(
                    item,
                    commentRepository.findCommentsByItemId(item.getId()),
                    bookingRepository.getLastEndedBooking(item.getId()).orElse(new Booking()),
                    bookingRepository.getNextBooking(item.getId()).orElse(new Booking())
                    )
            );
        }

        return itemWithDateTimeDtoList;
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findItemById(itemId).orElseThrow(
                () -> new NotFoundException("Вещь с id=" + itemId + " не найдена"));
        itemRepository.delete(item);
    }

    @Override
    public ItemWithDateTimeDto getItemWithComments(Long itemId) {
        Item item = itemRepository.findItemById(itemId).orElseThrow(
                        () -> new NotFoundException("Вещь с id=" + itemId + " не найдена")
        );

        return ItemMapper.toItemWithDatesDto(
                item,
                commentRepository.findCommentsByItemId(itemId),
                bookingRepository.getLastEndedBooking(item.getId()).orElse(new Booking()),
                bookingRepository.getNextBooking(item.getId()).orElse(new Booking())
        );
    }
}
