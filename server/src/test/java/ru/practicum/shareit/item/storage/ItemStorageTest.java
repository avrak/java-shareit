package ru.practicum.shareit.item.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ItemStorageTest {
    User owner = new User();
    User user = new User();
    Item item = new Item();
    Item item2 = new Item();

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    public void beforeEach() {
        owner.setId(null);
        owner.setName("ItemStorageTest");
        owner.setEmail("ItemStorageTest@example.com");

        user.setId(null);
        user.setName("user");
        user.setEmail("user@example.com");

        item.setId(null);
        item.setName("ItemStorageTest_1");
        item.setDescription("ItemStorageTest_description_1");
        item.setAvailable(true);
        item.setOwnerId(1L);
        item.setRequestId(11L);

        item2.setId(null);
        item2.setName("ItemStorageTest_2");
        item2.setDescription("ItemStorageTest_description_2");
        item2.setAvailable(true);
        item2.setOwnerId(1L);
        item2.setRequestId(12L);
    }

    @Test
    @DisplayName("Сохранить и найти существующую вещь по id")
    void findItemById_existing() {
        User savedOwner = userRepository.save(owner);
        item.setOwnerId(savedOwner.getId());
        Item savedItem = itemRepository.save(item);

        assertEquals(item.getName(), savedItem.getName());
        assertEquals(Optional.of(savedItem), itemRepository.findItemById(savedItem.getId()));
    }

    @Test
    @DisplayName("Найти несуществующую вещь по id")
    void findItemById_notExisting() {

        assertEquals(Optional.empty(), itemRepository.findItemById(-1L));
    }

    @Test
    @DisplayName("Обновить вещь")
    void updateItem_correctly() {
        User savedOwner = userRepository.save(owner);
        item.setOwnerId(savedOwner.getId());
        Item savedItem = itemRepository.save(item);

        savedItem.setName("updateItem");
        savedItem.setDescription("updateItem_correctly");
        savedItem.setAvailable(false);
        Item updatedItem = itemRepository.save(savedItem);

        assertEquals("updateItem", updatedItem.getName());
        assertEquals("updateItem_correctly", updatedItem.getDescription());
        assertEquals(false, updatedItem.getAvailable());
    }

    @Test
    @DisplayName("Получить все вещи пользователя")
    void getItemListByOwner_ofUserWithItems() {
        User savedOwner = userRepository.save(owner);

        item.setOwnerId(savedOwner.getId());
        itemRepository.save(item);

        item2.setOwnerId(savedOwner.getId());
        itemRepository.save(item2);

        Collection<Item> itemList = itemRepository.findItemListByOwnerId(savedOwner.getId());

        assertEquals(2, itemList.size());
        assertTrue(itemList.toString().contains("ItemStorageTest_1"));
        assertTrue(itemList.toString().contains("ItemStorageTest_2"));
    }

    @Test
    @DisplayName("Запросить вещи пользователя, у которого их нет")
    void getItemListByOwner_ofUserWithoutItems() {
        User savedOwner = userRepository.save(owner);
        User savedUser = userRepository.save(user);

        item.setOwnerId(savedOwner.getId());
        itemRepository.save(item);

        item2.setOwnerId(savedOwner.getId());
        itemRepository.save(item2);

        Collection<Item> itemList = itemRepository.findItemListByOwnerId(savedUser.getId());

        assertTrue(itemList.isEmpty());
    }

    @Test
    @DisplayName("Получить вещи по совпадающему и несовпадающему описанию")
    void getItemListByText_matching() {
        User savedOwner = userRepository.save(owner);
        User savedUser = userRepository.save(user);

        item.setOwnerId(savedOwner.getId());
        itemRepository.save(item);

        item2.setOwnerId(savedOwner.getId());
        itemRepository.save(item2);

        assertEquals(1, itemRepository.findItemListByText("ItemStorageTest_1").size());
        assertEquals(0, itemRepository.findItemListByText("ItemStorageTest_X").size());
    }

    @Test
    @DisplayName("Удалить вещь")
    void deleteItem_test() {
        User savedOwner = userRepository.save(owner);

        item.setOwnerId(savedOwner.getId());
        Item savedItem = itemRepository.save(item);
        itemRepository.delete(savedItem);

        assertTrue(itemRepository.findItemById(savedItem.getId()).isEmpty());
    }
}
