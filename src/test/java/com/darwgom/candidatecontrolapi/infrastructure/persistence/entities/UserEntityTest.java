package com.darwgom.candidatecontrolapi.infrastructure.persistence.entities;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.persistence.PersistenceException;
import java.time.LocalDateTime;

@DataJpaTest
public class UserEntityTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldNotAllowNullUsernameAndPassword() {
        UserEntity user = new UserEntity();
        user.setRole(RoleEnum.ROLE_ADMIN);

        assertThrows(PersistenceException.class, () -> testEntityManager.persistFlushFind(user));
    }

    @Test
    public void shouldEnforceUniqueUsername() {
        String username = "uniqueUsername";
        UserEntity user1 = new UserEntity();
        user1.setUsername(username);
        user1.setPassword("password1");
        user1.setRole(RoleEnum.ROLE_ADMIN);
        testEntityManager.persistFlushFind(user1);

        UserEntity user2 = new UserEntity();
        user2.setUsername(username);
        user2.setPassword("password2");
        user2.setRole(RoleEnum.ROLE_ADMIN);

        assertThrows(PersistenceException.class, () -> testEntityManager.persistFlushFind(user2));
    }

    @Test
    public void shouldGenerateId() {
        UserEntity user = new UserEntity();
        user.setUsername("newuser");
        user.setPassword("newpassword");
        user.setRole(RoleEnum.ROLE_ADMIN);

        testEntityManager.persistFlushFind(user);

        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void shouldSetCreatedAt() {
        UserEntity user = new UserEntity();
        user.setUsername("anotheruser");
        user.setPassword("anotherpassword");
        user.setRole(RoleEnum.ROLE_ADMIN);

        testEntityManager.persistFlushFind(user);

        assertThat(user.getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldAllowSettingLastLogin() {
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = new UserEntity();
        user.setUsername("loginuser");
        user.setPassword("loginpassword");
        user.setRole(RoleEnum.ROLE_ADMIN);
        user.setLastLogin(now);

        testEntityManager.persistFlushFind(user);

        assertThat(user.getLastLogin()).isEqualTo(now);
    }

    @Test
    public void testNoArgsConstructor() {
        UserEntity user = new UserEntity();
        assertNotNull(user);
    }

    @Test
    public void testAllArgsConstructor() {
        UserEntity user = new UserEntity(1L, "john_doe", "password", RoleEnum.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now());
        assertNotNull(user);
        assertEquals("john_doe", user.getUsername());
    }

    @Test
    public void testGettersAndSetters() {
        UserEntity user = new UserEntity();
        LocalDateTime now = LocalDateTime.now();
        user.setId(1L);
        user.setUsername("jane_doe");
        user.setPassword("12345");
        user.setRole(RoleEnum.ROLE_ADMIN);
        user.setLastLogin(now);
        user.setCreatedAt(now);

        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("jane_doe", user.getUsername());
        assertEquals("12345", user.getPassword());
        assertEquals(RoleEnum.ROLE_ADMIN, user.getRole());
        assertEquals(now, user.getLastLogin());
        assertEquals(now, user.getCreatedAt());
    }

}

