package ru.practicum.shareit.request.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ItemRequestRepositoryTest {
    private final String userName = "ItemRequestRepositoryTest";
    private final String email = userName + "@example.com";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Test
    @DisplayName("Добавить запрос")
    void addItemRequest_validTest() {
        User user = userRepository.save(new User(null, userName, email));

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequestor(user);
        itemRequest.setDescription("addItemRequest_validTest");
        itemRequest.setCreatedAt(LocalDateTime.now());

        assertEquals("addItemRequest_validTest", itemRequestRepository.save(itemRequest).getDescription());
    }

    @Test
    @DisplayName("Найти запросы пользователя")
    void findItemRequestListByRequestorId_test() {
        User user = userRepository.save(new User(null, userName, email));

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setRequestor(user);
        itemRequest1.setDescription("findItemRequestListByRequestorId_test1");
        itemRequest1.setCreatedAt(LocalDateTime.now());
        itemRequestRepository.save(itemRequest1);

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setRequestor(user);
        itemRequest2.setDescription("findItemRequestListByRequestorId_test2");
        itemRequest2.setCreatedAt(LocalDateTime.now());
        itemRequestRepository.save(itemRequest2);

        User user2 = userRepository.save(
                new User(null,
                        "findItemRequestListByRequestorId_test",
                        "findItemRequestListByRequestorId_test@example.com"
                )
        );

        ItemRequest itemRequest3 = new ItemRequest();
        itemRequest3.setRequestor(user2);
        itemRequest3.setDescription("findItemRequestListByRequestorId_test3");
        itemRequest3.setCreatedAt(LocalDateTime.now());
        itemRequestRepository.save(itemRequest3);

        Collection<ItemRequest> requestsList = itemRequestRepository.findItemRequestListByRequestorId(user.getId());

        assertEquals(2, requestsList.size());

        assertThat(
                requestsList.stream()
                        .map(ItemRequest::getDescription)
                        .toList()
        ).contains("findItemRequestListByRequestorId_test1");

        assertThat(
                requestsList.stream()
                        .map(ItemRequest::getDescription)
                        .toList()
        ).contains("findItemRequestListByRequestorId_test2");
    }

}
