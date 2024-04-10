package com.darwgom.candidatecontrolapi.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;

public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private SecretKey key;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        jwtTokenProvider = new JwtTokenProvider();
        jwtTokenProvider.setKey(key);
        jwtTokenProvider.setValidityInMilliseconds(3600000);
    }

    @Test
    public void testCreateToken() {
        String username = "testUser";
        String role = "ROLE_ADMIN";

        String token = jwtTokenProvider.createToken(username, role);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testExtractJwtFromRequest_ValidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String tokenValue = "Bearer validToken";
        when(request.getHeader("Authorization")).thenReturn(tokenValue);

        String extractedToken = jwtTokenProvider.extractJwtFromRequest(request);

        assertEquals("validToken", extractedToken);
    }

    @Test
    public void testExtractJwtFromRequest_NoBearerToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("invalidToken");

        String extractedToken = jwtTokenProvider.extractJwtFromRequest(request);

        assertNull(extractedToken);
    }

    @Test
    public void testIsTokenValid_ValidToken() {
        String validToken = jwtTokenProvider.createToken("testUser", "ROLE_ADMIN");

        boolean isValid = jwtTokenProvider.isTokenValid(validToken);

        assertTrue(isValid);
    }

    @Test
    public void testIsTokenValid_ExpiredToken() {
        String expiredToken = jwtTokenProvider.createToken("testUser", "ROLE_ADMIN");
        try {
            jwtTokenProvider.setValidityInMilliseconds(-1);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean isValid = jwtTokenProvider.isTokenValid(expiredToken);

        assertTrue(isValid);
    }

    @Test
    public void testIsTokenValid_BlacklistedToken() {
        String token = jwtTokenProvider.createToken("testUser", "ROLE_ADMIN");
        jwtTokenProvider.invalidateToken(token);

        boolean isValid = jwtTokenProvider.isTokenValid(token);

        assertFalse(isValid);
    }


}
