package org.justjava.gymcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.justjava.gymcore.model.User;
import org.justjava.gymcore.model.UserRole;
import org.justjava.gymcore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void createUser_returnsCreatedUser() throws Exception {
        var user = new User("John Doe", "john@example.com", UserRole.MEMBER, null);
        var savedUser = new User("John Doe", "john@example.com", UserRole.MEMBER, null);
        savedUser.setId(1L);
        given(userService.createUser(user)).willReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.role").value("MEMBER"));

        verify(userService).createUser(user);
    }

    @Test
    void getUser_returnsUser() throws Exception {
        var user = new User("Jane Doe", "jane@example.com", UserRole.ADMIN, null);
        user.setId(2L);
        given(userService.getUser(2L)).willReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        verify(userService).getUser(2L);
    }

    @Test
    void getAllUsers_returnsList() throws Exception {
        var user1 = new User("Alice", "alice@example.com", UserRole.MEMBER, null);
        user1.setId(1L);
        var user2 = new User("Bob", "bob@example.com", UserRole.TRAINER, null);
        user2.setId(2L);
        var users = List.of(user1, user2);
        given(userService.getAllUsers()).willReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(userService).getAllUsers();
    }

    @Test
    void updateUser_returnsUpdatedUser() throws Exception {
        var updatedUser = new User("Updated Name", "updated@example.com", UserRole.MEMBER, null);
        updatedUser.setId(3L);
        given(userService.updateUser(3L, updatedUser)).willReturn(updatedUser);

        mockMvc.perform(put("/api/users/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.role").value("MEMBER"));

        verify(userService).updateUser(3L, updatedUser);
    }

    @Test
    void deleteUser_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/4"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(4L);
    }
}
