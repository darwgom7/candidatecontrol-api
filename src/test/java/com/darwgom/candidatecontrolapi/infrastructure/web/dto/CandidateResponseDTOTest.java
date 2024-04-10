package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CandidateResponseDTOTest {

    @Test
    public void gettersAndSetters_WorkAsExpected() {
        CandidateResponseDTO dto = new CandidateResponseDTO();

        dto.setId(1L);
        dto.setName("John Doe");
        dto.setEmail("johndoe@dev.com");
        dto.setGender(GenderEnum.MALE);
        dto.setSalaryExpected(BigDecimal.valueOf(50000));
        dto.setPhoneNumber("+1234567890");
        dto.setAddress("123 Main Street");
        dto.setCreatedAt(LocalDateTime.now());

        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getName());
        assertEquals("johndoe@dev.com", dto.getEmail());
        assertEquals(GenderEnum.MALE, dto.getGender());
        assertEquals(BigDecimal.valueOf(50000), dto.getSalaryExpected());
        assertEquals("+1234567890", dto.getPhoneNumber());
        assertEquals("123 Main Street", dto.getAddress());
        assertNotNull(dto.getCreatedAt());
    }

    @Test
    public void noArgsConstructor_CreatesInstance() {
        CandidateResponseDTO dto = new CandidateResponseDTO();

        assertNotNull(dto);
    }

    @Test
    public void allArgsConstructor_CreatesInstanceWithValues() {
        CandidateResponseDTO dto = new CandidateResponseDTO(1L, "John Doe",
                "johndoe@example.com", GenderEnum.MALE, BigDecimal.valueOf(50000),
                "+1234567890", "123 Main Street", LocalDateTime.now());

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getName());
        assertEquals("johndoe@example.com", dto.getEmail());
        assertEquals(GenderEnum.MALE, dto.getGender());
        assertEquals(BigDecimal.valueOf(50000), dto.getSalaryExpected());
        assertEquals("+1234567890", dto.getPhoneNumber());
        assertEquals("123 Main Street", dto.getAddress());
        assertNotNull(dto.getCreatedAt());
    }
}

