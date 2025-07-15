package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWideDto;
import ru.practicum.shareit.item.service.CommentServiceImpl;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @Mock
    ItemServiceImpl itemService;

    @Mock
    CommentServiceImpl commentService;

    @InjectMocks
    ItemController itemController;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    ItemDto itemDto = new ItemDto();
    ItemWideDto itemWideDto = new ItemWideDto();
    CommentDto commentDto;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
                .build();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String name = "UserServiceTest";
        String email = name + "@example.com";
        UserDto userDto = new UserDto(1L, name, email);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime startLast = LocalDateTime.now().minus(Duration.ofSeconds(10L));
        LocalDateTime endLast = LocalDateTime.now().minus(Duration.ofSeconds(1L));
        LocalDateTime startNext = LocalDateTime.now().plus(Duration.ofSeconds(1L));
        LocalDateTime endNext = LocalDateTime.now().plus(Duration.ofSeconds(10L));

        BookingDto bookingLastDto = new BookingDto();
        bookingLastDto.setId(1L);
        bookingLastDto.setItem(itemDto);
        bookingLastDto.setBooker(userDto);
        bookingLastDto.setStatus(Statuses.APPROVED);
        bookingLastDto.setStart(startLast);
        bookingLastDto.setEnd(endLast);

        BookingDto bookingNextDto = new BookingDto();
        bookingNextDto.setId(1L);
        bookingNextDto.setItem(itemDto);
        bookingNextDto.setBooker(userDto);
        bookingNextDto.setStatus(Statuses.APPROVED);
        bookingNextDto.setStart(startNext);
        bookingNextDto.setEnd(endNext);

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setItemId(1L);
        commentDto.setUser(1L);
        commentDto.setAuthorName(userDto.getName());
        commentDto.setCreated(createdAt);
        commentDto.setText("ItemServiceTest");

        itemDto.setId(1L);
        itemDto.setName("ItemServiceTest");
        itemDto.setDescription("ItemServiceTest description");
        itemDto.setOwnerId(1L);
        itemDto.setAvailable(true);
        itemDto.setRequestId(20L);

        itemWideDto = new ItemWideDto();
        itemWideDto.setId(1L);
        itemWideDto.setName("ItemServiceTest");
        itemWideDto.setDescription("ItemServiceTest description");
        itemWideDto.setOwner(1L);
        itemWideDto.setAvailable(true);
        itemWideDto.setRequest(20L);
        itemWideDto.setLastBooking(bookingLastDto);
        itemWideDto.setNextBooking(bookingNextDto);
        itemWideDto.setComments(List.of(commentDto));

    }

    @Test
    @DisplayName("Сохранить вещь")
    void addItem_test() throws Exception {
        when(itemService.addItem(eq(1L), eq(itemDto))).thenReturn(itemDto);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.ownerId", is(itemDto.getOwnerId()), Long.class))
                .andExpect(jsonPath("$.requestId", is(itemDto.getRequestId()), Long.class));

        verify(itemService).addItem(eq(1L), eq(itemDto));

    }

    @Test
    @DisplayName("Обновить вещь")
    void updateItem_test() throws Exception {
        when(itemService.updateItem(eq(1L), eq(1L), eq(itemDto))).thenReturn(itemDto);

        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.ownerId", is(itemDto.getOwnerId()), Long.class))
                .andExpect(jsonPath("$.requestId", is(itemDto.getRequestId()), Long.class));

        verify(itemService).updateItem(eq(1L), eq(1L), eq(itemDto));
    }

    @Test
    @DisplayName("Найти вещь по id")
    void getItemById_test() throws Exception {
        when(itemService.getItemWithComments(eq(1L), eq(1L))).thenReturn(itemWideDto);

        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemWideDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemWideDto.getName())))
                .andExpect(jsonPath("$.description", is(itemWideDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemWideDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$.owner", is(itemWideDto.getOwner()), Long.class))
                .andExpect(jsonPath("$.request", is(itemWideDto.getRequest()), Long.class));

        verify(itemService).getItemWithComments(eq(1L), eq(1L));
    }

    @Test
    @DisplayName("Найти все вещи пользователя")
    void getItemListByOwnerId_test() throws Exception {
        when(itemService.getItemListByOwner(eq(1L))).thenReturn(List.of(itemWideDto));

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemWideDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(itemWideDto.getName())))
                .andExpect(jsonPath("$[0].description", is(itemWideDto.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemWideDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$[0].owner", is(itemWideDto.getOwner()), Long.class))
                .andExpect(jsonPath("$[0].request", is(itemWideDto.getRequest()), Long.class));

        verify(itemService).getItemListByOwner(eq(1L));
    }

    @Test
    @DisplayName("Найти вещи по тексту")
    void getItemListByText_test() throws Exception {
        when(itemService.getItemListByText(any(String.class))).thenReturn(List.of(itemWideDto));

        mvc.perform(get("/items/search?text=text")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemWideDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(itemWideDto.getName())))
                .andExpect(jsonPath("$[0].description", is(itemWideDto.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemWideDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$[0].owner", is(itemWideDto.getOwner()), Long.class))
                .andExpect(jsonPath("$[0].request", is(itemWideDto.getRequest()), Long.class));

        verify(itemService).getItemListByText(any(String.class));
    }

    @Test
    @DisplayName("Добавить комментарий к вещи")
    void addComment_test() throws Exception {
        when(commentService.addComment(eq(1L), eq(1L), any(CommentDto.class))).thenReturn(commentDto);

        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.itemId", is(commentDto.getItemId()), Long.class))
                .andExpect(jsonPath("$.user", is(commentDto.getUser()), Long.class))
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.text", is(commentDto.getText())));

        verify(commentService).addComment(eq(1L), eq(1L), any(CommentDto.class));
    }
}
