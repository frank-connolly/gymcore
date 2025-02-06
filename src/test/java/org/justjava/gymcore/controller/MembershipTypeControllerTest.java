package org.justjava.gymcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.justjava.gymcore.model.MembershipType;
import org.justjava.gymcore.repository.MembershipTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MembershipTypeController.class)
class MembershipTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MembershipTypeRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createMembershipType_returnsCreatedMembershipType() throws Exception {
        var mt = new MembershipType("Premium", "Access to all classes", new BigDecimal("99.99"));
        var savedMt = new MembershipType("Premium", "Access to all classes", new BigDecimal("99.99"));
        savedMt.setId(1L);
        // Use any(MembershipType.class) matcher to avoid strict instance matching issues
        given(repository.save(any(MembershipType.class))).willReturn(savedMt);

        mockMvc.perform(post("/api/membership-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mt)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Premium"));

        verify(repository).save(any(MembershipType.class));
    }

    @Test
    void getMembershipType_returnsMembershipType() throws Exception {
        var mt = new MembershipType("Basic", "Basic membership", new BigDecimal("29.99"));
        mt.setId(2L);
        given(repository.findById(2L)).willReturn(Optional.of(mt));

        mockMvc.perform(get("/api/membership-types/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Basic"));

        verify(repository).findById(2L);
    }

    @Test
    void getAllMembershipTypes_returnsList() throws Exception {
        var mt1 = new MembershipType("Basic", "Basic membership", new BigDecimal("29.99"));
        mt1.setId(1L);
        var mt2 = new MembershipType("Premium", "Access to all classes", new BigDecimal("99.99"));
        mt2.setId(2L);
        var types = List.of(mt1, mt2);
        given(repository.findAll()).willReturn(types);

        mockMvc.perform(get("/api/membership-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(types.size()))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(repository).findAll();
    }
}
