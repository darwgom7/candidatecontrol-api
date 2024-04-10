package com.darwgom.candidatecontrolapi.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenFilterTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private SecretKey secretKey;

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() throws Exception {

        jwtTokenFilter = new JwtTokenFilter(secretKey);
        jwtTokenFilter.setJwtTokenProvider(jwtTokenProvider);
    }

    @Test
    void whenEndpointSecuredAndTokenPresent_thenShouldExtractToken() throws Exception {
        String token = "some-token#+";
        when(request.getRequestURI()).thenReturn("/api/secure/endpoint");
        when(jwtTokenProvider.extractJwtFromRequest(request)).thenReturn(token);
        when(jwtTokenProvider.isTokenValid(token)).thenReturn(true);

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        verify(jwtTokenProvider).extractJwtFromRequest(request);
        verify(jwtTokenProvider).isTokenValid(token);
    }


}
