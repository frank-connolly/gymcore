package org.justjava.gymcore.controller;

import org.justjava.gymcore.repository.MembershipTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MembershipTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MembershipTypeRepository repository;

    @BeforeEach
    public void setup() {
        // Clear all records before each test to ensure a clean test environment.
        repository.deleteAll();
    }

    @Test
    void testCreateAndGetMembershipType() throws Exception {
        // JSON payload to create a new MembershipType.
        String membershipJson = """
            {
                "name": "Premium",
                "description": "Access to all classes and facilities",
                "price": 99.99
            }
            """;

        // POST request to create a new membership type.
        mockMvc.perform(post("/api/membership-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(membershipJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Premium")));

        // GET request to retrieve all membership types.
        mockMvc.perform(get("/api/membership-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Premium")));
    }
}
