package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Statuses;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {
   @Mock
   private BookingServiceImpl bookingService;

   @InjectMocks
   private BookingController bookingController;

   private final ObjectMapper mapper = new ObjectMapper();

   private MockMvc mvc;

   BookingDto bookingDto = new BookingDto();

   @BeforeEach
   void beforeEach() {
      mvc = MockMvcBuilders
              .standaloneSetup(bookingController)
              .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
              .build();

      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      bookingDto = new BookingDto(
              1L,
              LocalDateTime.now().plusMinutes(10L),
              LocalDateTime.now().plusMinutes(20L),
              1L,
              1L,
              new ItemDto(
                      1L,
                      "BookingServiceTest",
                      "BookingServiceTest description",
                      true,
                      1L,
                      null,
                      null
              ),
              new UserDto(
                      1L,
                      "BookingServiceTest",
                      "BookingServiceTest@example.com"
              ),
              Statuses.APPROVED
      );
   }

   @Test
   @DisplayName("Сохранить бронирование")
   void saveBooking_test() throws Exception {
      when(bookingService.saveBooking(any(Long.class), any(BookingDto.class))).thenReturn(bookingDto);

      mvc.perform(post("/bookings")
                      .header("X-Sharer-User-Id", 1)
                      .content(mapper.writeValueAsString(bookingDto))
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
              .andExpect(jsonPath("$.itemId", is(bookingDto.getItemId()), Long.class))
              .andExpect(jsonPath("$.bookerId", is(bookingDto.getBookerId()), Long.class))
              .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class))
              .andExpect(jsonPath("$.item.name", is(bookingDto.getItem().getName())))
              .andExpect(jsonPath("$.item.description", is(bookingDto.getItem().getDescription())))
              .andExpect(jsonPath("$.item.available", is(bookingDto.getItem().getAvailable())))
              .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
              .andExpect(jsonPath("$.booker.name", is(bookingDto.getBooker().getName())))
              .andExpect(jsonPath("$.booker.email", is(bookingDto.getBooker().getEmail())));

      verify(bookingService).saveBooking(any(Long.class), any(BookingDto.class));
   }

   @Test
   @DisplayName("Подтвердить бронирование")
   void approveBookingById_test() throws Exception {
      when(bookingService.approveBookingById(any(Long.class), any(Long.class), eq(true))).thenReturn(bookingDto);

      mvc.perform(patch("/bookings/1?approved=true")
                      .header("X-Sharer-User-Id", 1)
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
              .andExpect(jsonPath("$.status", is(bookingDto.getStatus().name())));

      verify(bookingService).approveBookingById(any(Long.class), any(Long.class), eq(true));
   }

   @Test
   @DisplayName("Получить бронирование по id")
   void getBookingById_test() throws Exception {
      when(bookingService.getBookingById(any(Long.class), any(Long.class))).thenReturn(bookingDto);

      mvc.perform(get("/bookings/1")
              .header("X-Sharer-User-Id", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
              .andExpect(jsonPath("$.itemId", is(bookingDto.getItemId()), Long.class))
              .andExpect(jsonPath("$.bookerId", is(bookingDto.getBookerId()), Long.class))
              .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class))
              .andExpect(jsonPath("$.item.name", is(bookingDto.getItem().getName())))
              .andExpect(jsonPath("$.item.description", is(bookingDto.getItem().getDescription())))
              .andExpect(jsonPath("$.item.available", is(bookingDto.getItem().getAvailable())))
              .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
              .andExpect(jsonPath("$.booker.name", is(bookingDto.getBooker().getName())))
              .andExpect(jsonPath("$.booker.email", is(bookingDto.getBooker().getEmail())));

      verify(bookingService).getBookingById(any(Long.class), any(Long.class));
   }

   @Test
   @DisplayName("Получить бронирования пользователя")
   void getBookingListByBookerId_test() throws Exception {
      when(bookingService.getBookingListByBookerId(any(Long.class)))
              .thenReturn(new ArrayList<>(Collections.singletonList(bookingDto)));

      mvc.perform(get("/bookings")
                      .header("X-Sharer-User-Id", 1)
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class))
              .andExpect(jsonPath("$[0].itemId", is(bookingDto.getItemId()), Long.class))
              .andExpect(jsonPath("$[0].bookerId", is(bookingDto.getBookerId()), Long.class))
              .andExpect(jsonPath("$[0].item.id", is(bookingDto.getItem().getId()), Long.class))
              .andExpect(jsonPath("$[0].item.name", is(bookingDto.getItem().getName())))
              .andExpect(jsonPath("$[0].item.description", is(bookingDto.getItem().getDescription())))
              .andExpect(jsonPath("$[0].item.available", is(bookingDto.getItem().getAvailable())))
              .andExpect(jsonPath("$[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
              .andExpect(jsonPath("$[0].booker.name", is(bookingDto.getBooker().getName())))
              .andExpect(jsonPath("$[0].booker.email", is(bookingDto.getBooker().getEmail())));

      verify(bookingService).getBookingListByBookerId(any(Long.class));
   }

   @Test
   @DisplayName("Получить бронирования для владельца вещи")
   void getBookingListByOwnerIdAndStatus_test() throws Exception {
      when(bookingService.getBookingListByOwnerIdAndStatus(any(Long.class), any(String.class)))
              .thenReturn(new ArrayList<>(Collections.singletonList(bookingDto)));

      mvc.perform(get("/bookings/owner?state=APPROVED")
                      .header("X-Sharer-User-Id", 1)
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class))
              .andExpect(jsonPath("$[0].itemId", is(bookingDto.getItemId()), Long.class))
              .andExpect(jsonPath("$[0].bookerId", is(bookingDto.getBookerId()), Long.class))
              .andExpect(jsonPath("$[0].item.id", is(bookingDto.getItem().getId()), Long.class))
              .andExpect(jsonPath("$[0].item.name", is(bookingDto.getItem().getName())))
              .andExpect(jsonPath("$[0].item.description", is(bookingDto.getItem().getDescription())))
              .andExpect(jsonPath("$[0].item.available", is(bookingDto.getItem().getAvailable())))
              .andExpect(jsonPath("$[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
              .andExpect(jsonPath("$[0].booker.name", is(bookingDto.getBooker().getName())))
              .andExpect(jsonPath("$[0].booker.email", is(bookingDto.getBooker().getEmail())));

      verify(bookingService).getBookingListByOwnerIdAndStatus(any(Long.class), any(String.class));
   }
}
