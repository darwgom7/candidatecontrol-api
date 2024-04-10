package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestDTOTest {

    @Test
    public void gettersAndSetters_WorkAsExpected() {
        LoginRequestDTO dto = new LoginRequestDTO();

        dto.setUsername("testuser");
        dto.setPassword("testpassword");

        assertEquals("testuser", dto.getUsername());
        assertEquals("testpassword", dto.getPassword());
    }

    @Test
    public void noArgsConstructor_CreatesInstance() {
        LoginRequestDTO dto = new LoginRequestDTO();

        assertNotNull(dto);
    }

    @Test
    public void allArgsConstructor_CreatesInstanceWithValues() {
        LoginRequestDTO dto = new LoginRequestDTO("testuser", "testpassword");

        assertNotNull(dto);
        assertEquals("testuser", dto.getUsername());
        assertEquals("testpassword", dto.getPassword());
    }
}

