package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageResponseDTOTest {

    @Test
    public void gettersAndSetters_WorkAsExpected() {
        MessageResponseDTO dto = new MessageResponseDTO();

        dto.setMessage("Success");

        assertEquals("Success", dto.getMessage());
    }

    @Test
    public void noArgsConstructor_CreatesInstance() {
        MessageResponseDTO dto = new MessageResponseDTO();

        assertNotNull(dto);
    }

    @Test
    public void allArgsConstructor_CreatesInstanceWithValues() {
        MessageResponseDTO dto = new MessageResponseDTO("Error");

        assertNotNull(dto);
        assertEquals("Error", dto.getMessage());
    }
}

