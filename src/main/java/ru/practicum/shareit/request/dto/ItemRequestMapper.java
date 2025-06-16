package ru.practicum.shareit.request.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {
    public static ItemRequestDto toRequestDto(ItemRequest itemRequest) {
        ItemRequestDto requestDto = new ItemRequestDto();

        requestDto.setId(itemRequest.getId());
        requestDto.setDescription(itemRequest.getDescription());
        requestDto.setRequestor(UserMapper.toUserDto(itemRequest.getRequestor()));
        requestDto.setCreated(itemRequest.getCreatedAt());

        return requestDto;
    }

    public static ItemRequest toRequest(ItemRequestDto requestDto) {
        ItemRequest itemRequest = new ItemRequest();

        itemRequest.setId(requestDto.getId());
        itemRequest.setDescription(requestDto.getDescription());
        itemRequest.setRequestor(UserMapper.toUser(requestDto.getRequestor()));
        itemRequest.setCreatedAt(requestDto.getCreated());

        return itemRequest;
    }
}
