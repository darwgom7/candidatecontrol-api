package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenResponseDTOTest {

    @Test
    public void gettersAndSetters_WorkAsExpected() {
        TokenResponseDTO dto = new TokenResponseDTO();

        dto.setToken("xyz123");

        assertEquals("xyz123", dto.getToken());
    }

    @Test
    public void noArgsConstructor_CreatesInstance() {
        TokenResponseDTO dto = new TokenResponseDTO();

        assertNotNull(dto);
    }

    @Test
    public void allArgsConstructor_CreatesInstanceWithValues() {
        TokenResponseDTO dto = new TokenResponseDTO("abc456");

        assertNotNull(dto);
        assertEquals("abc456", dto.getToken());
    }
}

