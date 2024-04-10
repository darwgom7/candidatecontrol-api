package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserResponseDTOTest {

    @Test
    public void gettersAndSetters_WorkAsExpected() {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(1L);
        dto.setUsername("jena_doe");
        dto.setPassword("myPassword+");
        dto.setRole(RoleEnum.ROLE_ADMIN);
        dto.setCreatedAt(LocalDateTime.now());

        assertEquals(1L, dto.getId());
        assertEquals("jena_doe", dto.getUsername());
        assertEquals("myPassword+", dto.getPassword());
        assertEquals(RoleEnum.ROLE_ADMIN, dto.getRole());
        assertNotNull(dto.getCreatedAt());
    }

    @Test
    public void noArgsConstructor_CreatesInstance() {
        UserResponseDTO dto = new UserResponseDTO();

        assertNotNull(dto);
    }

    @Test
    public void allArgsConstructor_CreatesInstanceWithValues() {
        LocalDateTime createdAt = LocalDateTime.now();
        UserResponseDTO dto = new UserResponseDTO(1L, "jena_doe", "myPassword+", RoleEnum.ROLE_ADMIN, createdAt);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("jena_doe", dto.getUsername());
        assertEquals("myPassword+", dto.getPassword());
        assertEquals(RoleEnum.ROLE_ADMIN, dto.getRole());
        assertEquals(createdAt, dto.getCreatedAt());
    }
}

