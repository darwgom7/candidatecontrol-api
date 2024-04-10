package com.darwgom.candidatecontrolapi.infrastructure.persistence.adapters;

import com.darwgom.candidatecontrolapi.domain.models.User;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.UserEntity;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories.UserRepository;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class UserJpaAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @Test
    public void whenSaveUser_thenUserIsSaved() {
        User user = mock(User.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(modelMapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        User savedUser = userJpaAdapter.saveUser(user);

        assertNotNull(savedUser);
        verify(userRepository).save(userEntity);
        verify(modelMapper).map(user, UserEntity.class);
        verify(modelMapper).map(userEntity, User.class);
    }

    @Test
    public void whenFindUserById_thenUserIsReturned() {
        Long id = 1L;
        UserEntity userEntity = mock(UserEntity.class);
        User user = mock(User.class);
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

        when(userRepository.findById(id)).thenReturn(userEntityOptional);
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        Optional<User> foundUser = userJpaAdapter.findUserById(id);

        assertTrue(foundUser.isPresent());
        assertSame(user, foundUser.get());
        verify(userRepository).findById(id);
        verify(modelMapper).map(userEntity, User.class);
    }

    @Test
    public void whenFindAllUsers_thenAllUsersAreReturned() {
        UserEntity userEntity = mock(UserEntity.class);
        User user = mock(User.class);
        List<UserEntity> userEntityList = Arrays.asList(userEntity);

        when(userRepository.findAll()).thenReturn(userEntityList);
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        List<User> allUsers = userJpaAdapter.findAllUsers();

        assertFalse(allUsers.isEmpty());
        assertSame(user, allUsers.get(0));
        verify(userRepository).findAll();
        verify(modelMapper).map(userEntity, User.class);
    }

    @Test
    public void whenUpdateUser_thenUserIsUpdated() {
        User user = mock(User.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(modelMapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        User updatedUser = userJpaAdapter.updateUser(user);

        assertNotNull(updatedUser);
        verify(userRepository).save(userEntity);
        verify(modelMapper).map(user, UserEntity.class);
        verify(modelMapper).map(userEntity, User.class);
    }

    @Test
    public void whenFindUserByUsername_thenUserIsReturned() {
        String username = "john_doe";
        UserEntity userEntity = mock(UserEntity.class);
        User user = mock(User.class);
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

        when(userRepository.findByUsername(username)).thenReturn(userEntityOptional);
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        User foundUser = userJpaAdapter.findByUsername(username);

        assertNotNull(foundUser);
        verify(userRepository).findByUsername(username);
        verify(modelMapper).map(userEntity, User.class);
    }

    @Test
    public void whenDeleteUser_thenUserIsDeleted() {
        Long id = 1L;
        doNothing().when(userRepository).deleteById(id);

        userJpaAdapter.deleteUser(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    public void whenCheckExistsByUsername_thenCorrectResultReturned() {
        String username = "john_doe";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean exists = userJpaAdapter.existsByUsername(username);

        assertTrue(exists);
        verify(userRepository).existsByUsername(username);
    }
}
