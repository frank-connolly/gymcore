package org.justjava.gymcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.model.User;
import org.justjava.gymcore.model.UserRole;
import org.justjava.gymcore.service.GymClassService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GymClassController.class)
class GymClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GymClassService gymClassService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void createGymClass_returnsCreatedGymClass() throws Exception {
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(10L);
        var gymClass = new GymClass("Yoga", "Morning Yoga", LocalDateTime.of(2025, 2, 5, 8, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 20, trainer);
        var savedGymClass = new GymClass("Yoga", "Morning Yoga", LocalDateTime.of(2025, 2, 5, 8, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 20, trainer);
        savedGymClass.setId(1L);

        given(gymClassService.createGymClass(gymClass)).willReturn(savedGymClass);

        mockMvc.perform(post("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gymClass)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Yoga"))
                .andExpect(jsonPath("$.description").value("Morning Yoga"));

        verify(gymClassService).createGymClass(gymClass);
    }

    @Test
    void getGymClass_returnsGymClass() throws Exception {
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(10L);
        var gymClass = new GymClass("Pilates", "Evening Pilates", LocalDateTime.of(2025, 2, 5, 18, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 15, trainer);
        gymClass.setId(2L);
        given(gymClassService.getGymClass(2L)).willReturn(Optional.of(gymClass));

        mockMvc.perform(get("/api/classes/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("Pilates"))
                .andExpect(jsonPath("$.description").value("Evening Pilates"));

        verify(gymClassService).getGymClass(2L);
    }

    @Test
    void getAllGymClasses_returnsList() throws Exception {
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(10L);
        var gc1 = new GymClass("Yoga", "Morning Yoga", LocalDateTime.of(2025, 2, 5, 8, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 20, trainer);
        gc1.setId(1L);
        var gc2 = new GymClass("Pilates", "Evening Pilates", LocalDateTime.of(2025, 2, 5, 18, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 15, trainer);
        gc2.setId(2L);
        var gymClasses = List.of(gc1, gc2);
        given(gymClassService.getAllGymClasses()).willReturn(gymClasses);

        mockMvc.perform(get("/api/classes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(gymClasses.size()))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(gymClassService).getAllGymClasses();
    }

    @Test
    void updateGymClass_returnsUpdatedGymClass() throws Exception {
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(10L);
        var updatedGymClass = new GymClass("Zumba", "Dance workout", LocalDateTime.of(2025, 2, 6, 9, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 25, trainer);
        updatedGymClass.setId(3L);
        given(gymClassService.updateGymClass(3L, updatedGymClass)).willReturn(updatedGymClass);

        mockMvc.perform(put("/api/classes/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGymClass)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.title").value("Zumba"))
                .andExpect(jsonPath("$.description").value("Dance workout"));

        verify(gymClassService).updateGymClass(3L, updatedGymClass);
    }

    @Test
    void deleteGymClass_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/classes/4"))
                .andExpect(status().isNoContent());

        verify(gymClassService).deleteGymClass(4L);
    }
}
