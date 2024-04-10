package com.darwgom.candidatecontrolapi.infrastructure.persistence.entities;

import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import jakarta.persistence.PersistenceException;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CandidateEntityTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldNotAllowNullName() {
        CandidateEntity candidate = new CandidateEntity();
        candidate.setEmail("test@test.com");
        candidate.setGender(GenderEnum.MALE);
        candidate.setSalaryExpected(new BigDecimal(50000));
        candidate.setPhoneNumber("1234567890");

        assertThrows(PersistenceException.class, () -> testEntityManager.persistFlushFind(candidate));
    }

    @Test
    public void shouldGenerateId() {
        CandidateEntity candidate = new CandidateEntity();
        candidate.setName("John Doe");
        candidate.setEmail("john.doe@dev.com");
        candidate.setGender(GenderEnum.MALE);
        candidate.setSalaryExpected(new BigDecimal(60000));
        candidate.setPhoneNumber("1234567890");

        testEntityManager.persistFlushFind(candidate);

        assertThat(candidate.getId()).isNotNull();
    }

    @Test
    public void shouldSetCreatedAt() {
        CandidateEntity candidate = new CandidateEntity();
        candidate.setName("Jane Doe");
        candidate.setEmail("jane.doe@dev.com");
        candidate.setGender(GenderEnum.FEMALE);
        candidate.setSalaryExpected(new BigDecimal(70000));
        candidate.setPhoneNumber("0987654321");

        testEntityManager.persistFlushFind(candidate);

        assertThat(candidate.getCreatedAt()).isNotNull();
    }

    @Test
    public void testNoArgsConstructor() {
        CandidateEntity candidate = new CandidateEntity();
        assertNotNull(candidate);
    }

    @Test
    public void testAllArgsConstructor() {
        CandidateEntity candidate = new CandidateEntity(1L, "John Doe", "john.doe@dev.com", GenderEnum.MALE, new BigDecimal(500000), "1234567890", "123 Main St", LocalDateTime.now());
        assertNotNull(candidate);
        assertEquals("John Doe", candidate.getName());
    }

    @Test
    public void testGettersAndSetters() {
        CandidateEntity candidate = new CandidateEntity();
        LocalDateTime now = LocalDateTime.now();
        candidate.setId(1L);
        candidate.setName("Jane Doe");
        candidate.setEmail("jane.doe@test.com");
        candidate.setGender(GenderEnum.FEMALE);
        candidate.setSalaryExpected(new BigDecimal(70000));
        candidate.setPhoneNumber("0987654321");
        candidate.setAddress("456 Elm St");
        candidate.setCreatedAt(now);

        assertEquals(1L, candidate.getId());
        assertEquals("Jane Doe", candidate.getName());
        assertEquals("jane.doe@test.com", candidate.getEmail());
        assertEquals(GenderEnum.FEMALE, candidate.getGender());
        assertEquals(BigDecimal.valueOf(70000), candidate.getSalaryExpected());
        assertEquals("0987654321", candidate.getPhoneNumber());
        assertEquals("456 Elm St", candidate.getAddress());
        assertEquals(now, candidate.getCreatedAt());
    }

}

