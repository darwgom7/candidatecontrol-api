package com.darwgom.candidatecontrolapi.infrastructure.web.controllers;

import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.CandidateEntity;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories.CandidateRepository;
import com.darwgom.candidatecontrolapi.infrastructure.security.JwtTokenProvider;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CandidateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        candidateRepository.deleteAll();
    }

    @Test
    public void testCreateCandidate() throws Exception {
        CandidateRequestDTO candidateRequestDTO = new CandidateRequestDTO("Jane Doe", "jane@dev.com", GenderEnum.MALE.name(), new BigDecimal(8), "1234567890", null);

        mockMvc.perform(post("/api/candidates")
                        .header("Authorization", "Bearer " + jwtTokenProvider.createToken("adminuser", RoleEnum.ROLE_ADMIN.name()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@dev.com"));
    }

    @Test
    public void testGetCandidate() throws Exception {
        CandidateEntity candidate = new CandidateEntity(null, "John Doe", "john@example.com", GenderEnum.MALE, null, null, null, null);
        CandidateEntity savedCandidate = candidateRepository.save(candidate);

        mockMvc.perform(get("/api/candidates/{id}", savedCandidate.getId())
                        .header("Authorization", "Bearer " + jwtTokenProvider.createToken("adminuser", RoleEnum.ROLE_ADMIN.name())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedCandidate.getId()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testGetAllCandidates() throws Exception {
        CandidateEntity candidate1 = new CandidateEntity(null, "John Doe", "john@example.com", GenderEnum.MALE, null, null, null, null);
        CandidateEntity candidate2 = new CandidateEntity(null, "Jane Smith", "jane@example.com", GenderEnum.FEMALE, null, null, null, null);
        candidateRepository.save(candidate1);
        candidateRepository.save(candidate2);

        mockMvc.perform(get("/api/candidates")
                        .header("Authorization", "Bearer " + jwtTokenProvider.createToken("adminuser", RoleEnum.ROLE_ADMIN.name())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    public void testUpdateCandidate() throws Exception {
        CandidateEntity candidate = new CandidateEntity(null, "John Doe", "john@dev.com", GenderEnum.MALE, null, null, null, null);
        CandidateEntity savedCandidate = candidateRepository.save(candidate);

        CandidateRequestDTO candidateRequestDTO = new CandidateRequestDTO("John Updated", "john.updated@dev.com", GenderEnum.MALE.name(), null, null, null);

        mockMvc.perform(put("/api/candidates/{id}", savedCandidate.getId())
                        .header("Authorization", "Bearer " + jwtTokenProvider.createToken("adminuser", RoleEnum.ROLE_ADMIN.name()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedCandidate.getId()))
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@dev.com"));
    }

    @Test
    public void testDeleteCandidate() throws Exception {
        CandidateEntity candidate = new CandidateEntity(null, "John Doe", "john@test.com", GenderEnum.MALE, null, null, null, null);
        CandidateEntity savedCandidate = candidateRepository.save(candidate);

        mockMvc.perform(delete("/api/candidates/{id}", savedCandidate.getId())
                        .header("Authorization", "Bearer " + jwtTokenProvider.createToken("adminuser", RoleEnum.ROLE_ADMIN.name())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Candidate deleted successfully"));

        Optional<CandidateEntity> deletedCandidate = candidateRepository.findById(savedCandidate.getId());
        assertFalse(deletedCandidate.isPresent());
    }

}
