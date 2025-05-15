package ru.practicum.shareit.request.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {
    public static ItemRequestDto toRequestDto(ItemRequest request) {
        ItemRequestDto requestDto = new ItemRequestDto();

        requestDto.setId(request.getId());
        requestDto.setDescription(request.getDescription());
        requestDto.setRequestor(request.getRequestor());
        requestDto.setCreated(request.getCreated());

        return requestDto;
    }

    public static ItemRequest toRequest(ItemRequestDto requestDto) {
        ItemRequest request = new ItemRequest();

        request.setId(requestDto.getId());
        request.setDescription(requestDto.getDescription());
        request.setRequestor(requestDto.getRequestor());
        request.setCreated(requestDto.getCreated());

        return request;
    }
}
