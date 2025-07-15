package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest {
    private final String name = "UserServiceTest";
    private final String email = name + "@example.com";
    private final LocalDateTime createdAt = LocalDateTime.now();

    User user = new User(1L, name, email);
    UserDto userDto = new UserDto(1L, name, email);
    ItemRequest itemRequest = new ItemRequest();
    ItemRequestDto itemRequestDto = new ItemRequestDto();

    @Mock
    ItemRequestRepository itemRequestRepository;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    ItemRequestServiceImpl requestService;

    @BeforeEach
    void beforeEach() {
        itemRequest.setId(1L);
        itemRequest.setRequestor(user);
        itemRequest.setDescription("ItemRequestServiceTest");
        itemRequest.setCreatedAt(createdAt);

        itemRequestDto.setId(1L);
        itemRequestDto.setRequestor(userDto);
        itemRequestDto.setDescription("ItemRequestServiceTest");
        itemRequestDto.setCreated(createdAt);
    }

    @Test
    @DisplayName("Добавить запрос")
    void addItemRequest_test() {
        when(userService.getUserById(any())).thenReturn(userDto);
        when(itemRequestRepository.save(any())).thenReturn(itemRequest);

        assertEquals(itemRequestDto.getDescription(),
                requestService.addItemRequest(1L, itemRequestDto).getDescription());

        verify(userService).getUserById(1L);
        verify(itemRequestRepository).save(any());
    }

    @Test
    @DisplayName("Найти все запросы существующего пользователя")
    void getMyItemRequests_validUser() {
        Collection<ItemRequest> requestList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ItemRequest newItemRequest = new ItemRequest();

            newItemRequest.setId(1L);
            newItemRequest.setRequestor(user);
            newItemRequest.setDescription("getMyItemRequests_Test" + i);
            newItemRequest.setCreatedAt(LocalDateTime.now());

            requestList.add(newItemRequest);
        }

        when(itemRequestRepository.findItemRequestListByRequestorId(any())).thenReturn(requestList);

        var myRequestsList = requestService.getMyItemRequests(1L).stream().toList();

        assertEquals(3, myRequestsList.size());
        assertEquals("getMyItemRequests_Test0", myRequestsList.get(0).getDescription());
        assertEquals("getMyItemRequests_Test1", myRequestsList.get(1).getDescription());
        assertEquals("getMyItemRequests_Test2", myRequestsList.get(2).getDescription());
        verify(itemRequestRepository).findItemRequestListByRequestorId(1L);
    }

    @Test
    @DisplayName("Найти все запросы несуществующего пользователя")
    void getMyItemRequests_invalidUser() {

        when(itemRequestRepository.findItemRequestListByRequestorId(any())).thenReturn(new ArrayList<>());

        var myRequestsList = requestService.getMyItemRequests(1L).stream().toList();

        assertEquals(0, myRequestsList.size());
        verify(itemRequestRepository).findItemRequestListByRequestorId(1L);
    }

    @Test
    @DisplayName("Найти все запросы")
    void getAllItemRequests_Test() {
        List<ItemRequest> requestList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ItemRequest newItemRequest = new ItemRequest();

            newItemRequest.setId(1L);
            newItemRequest.setRequestor(user);
            newItemRequest.setDescription("getMyItemRequests_Test" + i);
            newItemRequest.setCreatedAt(LocalDateTime.now());

            requestList.add(newItemRequest);
        }

        when(itemRequestRepository.findAll()).thenReturn(requestList);

        var myRequestsList = requestService.getAllItemRequests().stream().toList();

        assertEquals(3, myRequestsList.size());
        assertEquals("getMyItemRequests_Test0", myRequestsList.get(0).getDescription());
        assertEquals("getMyItemRequests_Test1", myRequestsList.get(1).getDescription());
        assertEquals("getMyItemRequests_Test2", myRequestsList.get(2).getDescription());
        verify(itemRequestRepository).findAll();
    }

    @Test
    @DisplayName("Найти существующий запрос")
    void getItemRequestById_existingRequest() {
        when(itemRequestRepository.findItemRequestById(any())).thenReturn(Optional.of(itemRequest));

        assertEquals(itemRequestDto.getDescription(), requestService.getItemRequestById(1L).getDescription());
        verify(itemRequestRepository).findItemRequestById(1L);
    }

    @Test
    @DisplayName("Найти несуществующий запрос")
    void getItemRequestById_notExistingRequest() {
        when(itemRequestRepository.findItemRequestById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> requestService.getItemRequestById(1L));
        verify(itemRequestRepository).findItemRequestById(1L);
    }
}
