package ru.practicum.shareit.request;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemRequestControllerTest {
    @Mock
    ItemRequestServiceImpl requestService;

    @InjectMocks
    ItemRequestController requestController;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    ItemRequestDto requestDto;
    ItemRequestDto requestDto2;
    ItemRequestDto requestDto3;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders
                .standaloneSetup(requestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
                .build();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        requestDto = new ItemRequestDto(
                1L,
                "itemRequestDto_valid",
                new UserDto(1L, "ItemRequestControllerTest", "ItemRequestControllerTest@example.com"),
                LocalDateTime.now(),
                new ArrayList<ItemShortDto>()
        );
        requestDto2 = new ItemRequestDto(
                2L,
                "itemRequestDto_valid2",
                new UserDto(1L, "ItemRequestControllerTest2", "ItemRequestControllerTest2@example.com"),
                LocalDateTime.now(),
                new ArrayList<ItemShortDto>()
        );
        requestDto3 = new ItemRequestDto(
                3L,
                "itemRequestDto_valid3",
                new UserDto(1L, "ItemRequestControllerTest3", "ItemRequestControllerTest3@example.com"),
                LocalDateTime.now(),
                new ArrayList<ItemShortDto>()
        );
    }

    @Test
    @DisplayName("Создать корректный запрос")
    void addItemRequest_valid() throws Exception {
        when(requestService.addItemRequest(any(Long.class), any(ItemRequestDto.class))).thenReturn(requestDto);

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestDto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(requestDto.getDescription())))
                .andExpect(jsonPath("$.created",
                        is(requestDto.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                );

        verify(requestService).addItemRequest(any(Long.class), any(ItemRequestDto.class));
    }

    @Test
    @DisplayName("Получить все запросы пользователя")
    void getMyItemRequests() throws Exception {
        when(requestService.getMyItemRequests(any(Long.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(requestDto, requestDto2)));

        mvc.perform(get("/requests")
                    .header("X-Sharer-User-Id", 1L)
                    .content(mapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(requestDto.getDescription())))
                .andExpect(jsonPath("$[0].created",
                        is(requestDto.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                );

        verify(requestService).getMyItemRequests(1L);
    }

    @Test
    @DisplayName("Получить все запросы")
    void getAllItemRequests() throws Exception {
        when(requestService.getAllItemRequests())
                .thenReturn(new ArrayList<>(Arrays.asList(requestDto, requestDto2)));

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(requestDto.getDescription())))
                .andExpect(jsonPath("$[0].created",
                        is(requestDto.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
        );

        verify(requestService).getAllItemRequests();
    }

    @Test
    @DisplayName("Получить запрос по id")
    void getItemRequestById() throws Exception {
        when(requestService.getItemRequestById(any(Long.class))).thenReturn(requestDto3);

        mvc.perform(get("/requests/3")
                        .header("X-Sharer-User-Id", 3L)
                        .content(mapper.writeValueAsString(requestDto3))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestDto3.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(requestDto3.getDescription())))
                .andExpect(jsonPath("$.created",
                        is(requestDto3.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                );

        verify(requestService).getItemRequestById(3L);
    }
}