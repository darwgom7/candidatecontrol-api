package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CandidateRequestDTOTest {

    @Test
    public void gettersAndSetters_WorkAsExpected() {
        CandidateRequestDTO dto = new CandidateRequestDTO();

        dto.setName("John Doe");
        dto.setEmail("johndoe@dev.com");
        dto.setGender("MALE");
        dto.setSalaryExpected(BigDecimal.valueOf(50000));
        dto.setPhoneNumber("+1234567890");
        dto.setAddress("123 Main Street");

        assertEquals("John Doe", dto.getName());
        assertEquals("johndoe@dev.com", dto.getEmail());
        assertEquals("MALE", dto.getGender());
        assertEquals(BigDecimal.valueOf(50000), dto.getSalaryExpected());
        assertEquals("+1234567890", dto.getPhoneNumber());
        assertEquals("123 Main Street", dto.getAddress());
    }

    @Test
    public void noArgsConstructor_CreatesInstance() {
        CandidateRequestDTO dto = new CandidateRequestDTO();

        assertNotNull(dto);
    }

    @Test
    public void allArgsConstructor_CreatesInstanceWithValues() {
        CandidateRequestDTO dto = new CandidateRequestDTO("John Doe", "johndoe@test.com",
                "MALE", BigDecimal.valueOf(50000), "+1234567890", "123 Main Street");

        assertNotNull(dto);
        assertEquals("John Doe", dto.getName());
        assertEquals("johndoe@test.com", dto.getEmail());
        assertEquals("MALE", dto.getGender());
        assertEquals(BigDecimal.valueOf(50000), dto.getSalaryExpected());
        assertEquals("+1234567890", dto.getPhoneNumber());
        assertEquals("123 Main Street", dto.getAddress());
    }
}

