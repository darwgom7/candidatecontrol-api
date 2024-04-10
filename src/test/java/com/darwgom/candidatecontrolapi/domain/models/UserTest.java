package com.darwgom.candidatecontrolapi.domain.models;

import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "john_doe", "password", RoleEnum.ROLE_ADMIN, now, now);

        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(RoleEnum.ROLE_ADMIN, user.getRole());
        assertEquals(now, user.getLastLogin());
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        user.setId(2L);
        user.setUsername("jane_doe");
        user.setPassword("123456");
        user.setRole(RoleEnum.ROLE_ADMIN);
        user.setLastLogin(now);
        user.setCreatedAt(now);

        assertEquals(Long.valueOf(2L), user.getId());
        assertEquals("jane_doe", user.getUsername());
        assertEquals("123456", user.getPassword());
        assertEquals(RoleEnum.ROLE_ADMIN, user.getRole());
        assertEquals(now, user.getLastLogin());
        assertEquals(now, user.getCreatedAt());
    }


}

