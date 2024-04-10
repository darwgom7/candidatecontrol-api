package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRequestDTOTest {

    @Test
    public void gettersAndSetters_WorkAsExpected() {
        UserRequestDTO dto = new UserRequestDTO();

        dto.setUsername("jena_doe");
        dto.setPassword("myPassword+");
        dto.setRole("ROLE_ADMIN");

        assertEquals("jena_doe", dto.getUsername());
        assertEquals("myPassword+", dto.getPassword());
        assertEquals("ROLE_ADMIN", dto.getRole());
    }

    @Test
    public void noArgsConstructor_CreatesInstance() {
        UserRequestDTO dto = new UserRequestDTO();

        assertNotNull(dto);
    }

    @Test
    public void allArgsConstructor_CreatesInstanceWithValues() {
        UserRequestDTO dto = new UserRequestDTO("jena_doe", "myPassword+", "ROLE_ADMIN");

        assertNotNull(dto);
        assertEquals("jena_doe", dto.getUsername());
        assertEquals("myPassword+", dto.getPassword());
        assertEquals("ROLE_ADMIN", dto.getRole());
    }
}

