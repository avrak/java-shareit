package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.model.ForbiddenException;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWideDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemService;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
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
    public ItemWideDto getItemById(Long itemId) {
        Item item = itemRepository
                .findItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id=" + itemId + " не найдена"));

        LocalDateTime now = LocalDateTime.now();

        return ItemMapper.toItemWideDto(
                item,
                commentRepository.findCommentsByItemId(itemId),
                bookingRepository.findFirstOneByItemAndStatusAndEndBeforeOrderByEndDesc(
                        item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking()),
                bookingRepository.findFirstOneByItemAndStatusAndStartAfterOrderByStartAsc(
                        item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking())

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
    public Collection<ItemWideDto> getItemListByOwner(Long ownerId) {
        Collection<ItemWideDto> itemWideDtoList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (Item item : itemRepository.findItemListByOwner(ownerId)) {
            itemWideDtoList.add(ItemMapper.toItemWideDto(
                    item,
                    commentRepository.findCommentsByItemId(item.getId()),
                    bookingRepository.findFirstOneByItemAndStatusAndEndBeforeOrderByEndDesc(
                            item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking()),
                    bookingRepository.findFirstOneByItemAndStatusAndStartAfterOrderByStartAsc(
                            item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking())
                    )
            );
        }

        return itemWideDtoList;
    }

    @Override
    public Collection<ItemWideDto> getItemListByText(String text) {
        Collection<ItemWideDto> itemWideDtoList = new ArrayList<>();

        if (text.isEmpty()) {
            return itemWideDtoList;
        }

        LocalDateTime now = LocalDateTime.now();

        for (Item item : itemRepository.findItemListByText(text)) {

            itemWideDtoList.add(ItemMapper.toItemWideDto(
                    item,
                    commentRepository.findCommentsByItemId(item.getId()),
                    bookingRepository.findFirstOneByItemAndStatusAndEndBeforeOrderByEndDesc(
                            item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking()),
                    bookingRepository.findFirstOneByItemAndStatusAndStartAfterOrderByStartAsc(
                            item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking())
                    )
            );
        }

        return itemWideDtoList;
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findItemById(itemId).orElseThrow(
                () -> new NotFoundException("Вещь с id=" + itemId + " не найдена"));
        itemRepository.delete(item);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemWideDto getItemWithComments(Long userId, Long itemId) {
        Item item = itemRepository.findItemById(itemId).orElseThrow(
                        () -> new NotFoundException("Вещь с id=" + itemId + " не найдена")
        );

        LocalDateTime now = LocalDateTime.now();

        Booking lastBooking = new Booking();
        Booking nextBooking = new Booking();

        if (userId.equals(item.getOwner())) {
            lastBooking = bookingRepository.findFirstOneByItemAndStatusAndEndBeforeOrderByEndDesc(
                    item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking());

             nextBooking = bookingRepository.findFirstOneByItemAndStatusAndStartAfterOrderByStartAsc(
                    item.getId(), Statuses.APPROVED.name(), now).orElse(new Booking());
        }

        return ItemMapper.toItemWideDto(
                item,
                commentRepository.findCommentsByItemId(itemId),
                lastBooking,
                nextBooking
        );
    }
}
