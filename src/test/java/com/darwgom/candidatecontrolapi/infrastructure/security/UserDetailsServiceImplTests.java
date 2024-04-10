package com.darwgom.candidatecontrolapi.infrastructure.security;

import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "testUser";
        com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.UserEntity userEntity =
                new com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("password");
        userEntity.setRole(RoleEnum.ROLE_ADMIN);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Collection<? extends GrantedAuthority> authoritiesCollection = userDetails.getAuthorities();
        List<GrantedAuthority> authoritiesList = new ArrayList<>(authoritiesCollection);

        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")), authoritiesList);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}

