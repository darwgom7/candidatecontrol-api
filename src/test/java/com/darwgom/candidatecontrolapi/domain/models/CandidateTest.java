package com.darwgom.candidatecontrolapi.domain.models;

import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class CandidateTest {

    @Test
    public void testNoArgsConstructor() {
        Candidate candidate = new Candidate();
        assertNotNull(candidate);
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal salary = new BigDecimal("50000");
        Candidate candidate = new Candidate(1L, "John Doe", "john@dev.com", GenderEnum.MALE, salary, "1234567890", "123 Main St", now);

        assertNotNull(candidate);
        assertEquals(Long.valueOf(1L), candidate.getId());
        assertEquals("John Doe", candidate.getName());
        assertEquals("john@dev.com", candidate.getEmail());
        assertEquals(GenderEnum.MALE, candidate.getGender());
        assertEquals(salary, candidate.getSalaryExpected());
        assertEquals("1234567890", candidate.getPhoneNumber());
        assertEquals("123 Main St", candidate.getAddress());
        assertEquals(now, candidate.getCreatedAt());
    }

    @Test
    public void testGettersAndSetters() {
        Candidate candidate = new Candidate();
        LocalDateTime now = LocalDateTime.now();
        BigDecimal salary = new BigDecimal("60000");

        candidate.setId(2L);
        candidate.setName("Jane Doe");
        candidate.setEmail("jane@test.com");
        candidate.setGender(GenderEnum.FEMALE);
        candidate.setSalaryExpected(salary);
        candidate.setPhoneNumber("0987654321");
        candidate.setAddress("456 Elm St");
        candidate.setCreatedAt(now);

        assertEquals(Long.valueOf(2L), candidate.getId());
        assertEquals("Jane Doe", candidate.getName());
        assertEquals("jane@test.com", candidate.getEmail());
        assertEquals(GenderEnum.FEMALE, candidate.getGender());
        assertEquals(salary, candidate.getSalaryExpected());
        assertEquals("0987654321", candidate.getPhoneNumber());
        assertEquals("456 Elm St", candidate.getAddress());
        assertEquals(now, candidate.getCreatedAt());
    }


}

