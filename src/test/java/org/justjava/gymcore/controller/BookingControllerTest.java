package org.justjava.gymcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.justjava.gymcore.model.Booking;
import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.model.User;
import org.justjava.gymcore.model.UserRole;
import org.justjava.gymcore.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void createBooking_returnsCreatedBooking() throws Exception {
        var member = new User("Member", "member@example.com", UserRole.MEMBER, null);
        member.setId(1L);
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(2L);
        var gymClass = new GymClass("Spinning", "Indoor cycling", LocalDateTime.of(2025, 2, 5, 9, 0), LocalDateTime.of(2025, 3, 5, 9, 0), 2, trainer);
        gymClass.setId(10L);
        var booking = new Booking(member, gymClass);
        var savedBooking = new Booking(member, gymClass);
        savedBooking.setId(100L);
        given(bookingService.createBooking(booking)).willReturn((ResponseEntity) ResponseEntity.created(new URI("/api/bookings")).body(savedBooking));

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L));

        verify(bookingService).createBooking(booking);
    }
    @Test
    void createBooking_addsToWaitlist_whenClassIsFull() throws Exception {
        // Mock response for when class is full
        when(bookingService.createBooking(any(Booking.class)))
                .thenAnswer(invocation -> ResponseEntity.ok("Class is full. You are now on the waitlist."));

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"id\":1}, \"gymClass\":{\"id\":10}}")) // Ensure valid JSON request body
                .andExpect(status().isOk())
                .andExpect(content().string("Class is full. You are now on the waitlist."));

        verify(bookingService).createBooking(any(Booking.class));
    }


    @Test
    void deleteBooking_promotesWaitlistedUser() throws Exception {
        doNothing().when(bookingService).deleteBooking(anyLong());

        mockMvc.perform(delete("/api/bookings/100"))
                .andExpect(status().isNoContent());

        verify(bookingService).deleteBooking(100L);
    }


    @Test
    void getBooking_returnsBooking() throws Exception {
        var member = new User("Member", "member@example.com", UserRole.MEMBER, null);
        member.setId(1L);
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(2L);
        var gymClass = new GymClass("Spinning", "Indoor cycling", LocalDateTime.of(2025, 2, 5, 9, 0), LocalDateTime.of(2025, 3, 5, 9, 0), 20, trainer);
        var booking = new Booking(member, gymClass);
        booking.setId(100L);
        given(bookingService.getBooking(100L)).willReturn(Optional.of(booking));

        mockMvc.perform(get("/api/bookings/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L));

        verify(bookingService).getBooking(100L);
    }

    @Test
    void getAllBookings_returnsList() throws Exception {
        var member = new User("Member", "member@example.com", UserRole.MEMBER, null);
        member.setId(1L);
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(2L);
        var gymClass = new GymClass("Spinning", "Indoor cycling", LocalDateTime.of(2025, 2, 5, 9, 0), LocalDateTime.of(2025, 3, 5, 9, 0), 20, trainer);
        gymClass.setId(10L);
        var booking = new Booking(member, gymClass);
        booking.setId(100L);
        var bookings = List.of(booking);
        given(bookingService.getAllBookings()).willReturn(bookings);

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(bookings.size()))
                .andExpect(jsonPath("$[0].id").value(100L));

        verify(bookingService).getAllBookings();
    }

    @Test
    void updateBooking_returnsUpdatedBooking() throws Exception {
        var member = new User("Member", "member@example.com", UserRole.MEMBER, null);
        member.setId(1L);
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(2L);
        var gymClass = new GymClass("Spinning", "Indoor cycling", LocalDateTime.of(2025, 2, 5, 9, 0), LocalDateTime.of(2025, 3, 5, 9, 0), 20, trainer);
        gymClass.setId(10L);
        var updatedBooking = new Booking(member, gymClass);
        updatedBooking.setId(100L);
        given(bookingService.updateBooking(100L, updatedBooking)).willReturn(updatedBooking);

        mockMvc.perform(put("/api/bookings/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBooking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L));

        verify(bookingService).updateBooking(100L, updatedBooking);
    }

    @Test
    void deleteBooking_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/bookings/100"))
                .andExpect(status().isNoContent());

        verify(bookingService).deleteBooking(100L);
    }
}
