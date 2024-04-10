package com.darwgom.candidatecontrolapi.infrastructure.web.controllers;

import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.UserEntity;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories.UserRepository;
import com.darwgom.candidatecontrolapi.infrastructure.security.JwtTokenProvider;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.UserRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("johndoe", "password", "ROLE_ADMIN");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    @Test
    public void testGetUser() throws Exception {
        UserEntity user = new UserEntity(null, "johndoe", "password", RoleEnum.ROLE_ADMIN, null, null);
        UserEntity savedUser = userRepository.save(user);

        mockMvc.perform(get("/api/users/{id}", savedUser.getId())
                        .header("Authorization", "Bearer " + jwtTokenProvider.createToken(savedUser.getUsername(), savedUser.getRole().name())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserEntity user1 = new UserEntity(null, "johndoe", "password", RoleEnum.ROLE_ADMIN, null, null);
        UserEntity user2 = new UserEntity(null, "janedoe", "password", RoleEnum.ROLE_ADMIN, null, null);
        userRepository.save(user1);
        userRepository.save(user2);

        String jwtToken = jwtTokenProvider.createToken(user1.getUsername(), user1.getRole().name());

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value("johndoe"))
                .andExpect(jsonPath("$[0].role").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$[1].username").value("janedoe"))
                .andExpect(jsonPath("$[1].role").value("ROLE_ADMIN"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserEntity user = new UserEntity(null, "johndoe", "password", RoleEnum.ROLE_ADMIN, null, null);
        UserEntity savedUser = userRepository.save(user);

        String jwtToken = jwtTokenProvider.createToken(savedUser.getUsername(), savedUser.getRole().name());

        UserRequestDTO userRequestDTO = new UserRequestDTO("johndoe", "newpassword", "ROLE_ADMIN");

        mockMvc.perform(put("/api/users/{id}", savedUser.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));

        UserEntity updatedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals("johndoe", updatedUser.getUsername());
        assertEquals("newpassword", updatedUser.getPassword());
        assertEquals(RoleEnum.ROLE_ADMIN, updatedUser.getRole());
    }

    @Test
    public void testDeleteUser() throws Exception {
        UserEntity user = new UserEntity(null, "johndoe", "password", RoleEnum.ROLE_ADMIN, null, null);
        UserEntity savedUser = userRepository.save(user);

        String jwtToken = jwtTokenProvider.createToken("adminuser", RoleEnum.ROLE_ADMIN.name());

        mockMvc.perform(delete("/api/users/{id}", savedUser.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"));

        Optional<UserEntity> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());
    }

}