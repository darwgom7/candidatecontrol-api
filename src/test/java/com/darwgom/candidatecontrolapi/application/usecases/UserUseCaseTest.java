package com.darwgom.candidatecontrolapi.application.usecases;

import com.darwgom.candidatecontrolapi.application.ports.out.IUserPort;
import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import com.darwgom.candidatecontrolapi.domain.models.User;
import com.darwgom.candidatecontrolapi.infrastructure.security.JwtTokenProvider;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private IUserPort userPort;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    public void loginUserTest() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("username", "password");
        Authentication authentication = mock(Authentication.class);
        User user = new User(1L, "username", "encodedPassword", RoleEnum.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now());
        String jwt = "jwtToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userPort.findByUsername("username")).thenReturn(user);
        when(jwtTokenProvider.createToken(user.getUsername(), user.getRole().name())).thenReturn(jwt);

        TokenResponseDTO result = userUseCase.loginUser(loginRequestDTO);

        assertNotNull(result);
        assertEquals(jwt, result.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createToken(user.getUsername(), user.getRole().name());
    }

    @Test
    public void registerUserTest() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("username", "password", "ROLE_ADMIN");
        User user = new User(1L, "username", "encodedPassword", RoleEnum.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now());
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(userPort.existsByUsername("username")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(modelMapper.map(userRequestDTO, User.class)).thenReturn(user);
        when(userPort.saveUser(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO result = userUseCase.registerUser(userRequestDTO);

        assertNotNull(result);
        verify(userPort).existsByUsername("username");
        verify(passwordEncoder).encode("password");
        verify(userPort).saveUser(user);
        verify(modelMapper).map(user, UserResponseDTO.class);
    }

    @Test
    public void getUserTest() {
        Long id = 1L;
        User user = new User(id, "username", "password", RoleEnum.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now());
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(userPort.findUserById(id)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO result = userUseCase.getUser(id);

        assertNotNull(result);
        verify(userPort).findUserById(id);
        verify(modelMapper).map(user, UserResponseDTO.class);
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = List.of(new User(1L, "username", "password", RoleEnum.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now()));
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(userPort.findAllUsers()).thenReturn(users);
        when(modelMapper.map(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponseDTO);

        List<UserResponseDTO> result = userUseCase.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(userPort).findAllUsers();
        verify(modelMapper, atLeastOnce()).map(any(User.class), eq(UserResponseDTO.class));
    }

    @Test
    public void whenUpdateUser_thenUserIsUpdated() {
        Long id = 1L;
        UserRequestDTO requestDTO = mock(UserRequestDTO.class);
        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setUsername("existingUsername");
        existingUser.setRole(RoleEnum.ROLE_ADMIN);
        User updatedUser = new User();
        UserResponseDTO responseDTO = new UserResponseDTO();

        when(requestDTO.getUsername()).thenReturn("newUsername");
        when(requestDTO.getRole()).thenReturn("ROLE_ADMIN");
        when(userPort.findUserById(id)).thenReturn(Optional.of(existingUser));
        when(userPort.saveUser(any(User.class))).thenReturn(updatedUser);

        doAnswer(invocation -> {
            UserRequestDTO dto = invocation.getArgument(0);
            User user = invocation.getArgument(1);
            user.setUsername(dto.getUsername());
            user.setRole(RoleEnum.valueOf("ROLE_ADMIN"));
            return null;
        }).when(modelMapper).map(any(UserRequestDTO.class), any(User.class));

        when(modelMapper.map(updatedUser, UserResponseDTO.class)).thenReturn(responseDTO);

        UserResponseDTO result = userUseCase.updateUser(id, requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(userPort).findUserById(id);
        verify(userPort).saveUser(existingUser);
        verify(modelMapper).map(requestDTO, existingUser);
        verify(modelMapper).map(updatedUser, UserResponseDTO.class);
    }


    @Test
    public void deleteUserTest() {
        Long id = 1L;
        User userToDelete = new User();

        when(userPort.findUserById(id)).thenReturn(Optional.of(userToDelete));
        doNothing().when(userPort).deleteUser(id);

        MessageResponseDTO result = userUseCase.deleteUser(id);

        assertNotNull(result);
        assertEquals("User deleted successfully", result.getMessage());
        verify(userPort).findUserById(id);
        verify(userPort).deleteUser(id);
    }


    @Test
    public void getCurrentUserTest() {
        String jwt = "mockedJWT";
        String username = "mockedUsername";
        User mockedUser = new User();
        UserResponseDTO mockedResponseDTO = new UserResponseDTO();

        when(jwtTokenProvider.getUsernameFromToken(jwt)).thenReturn(username);
        when(userPort.findByUsername(username)).thenReturn(mockedUser);
        when(modelMapper.map(mockedUser, UserResponseDTO.class)).thenReturn(mockedResponseDTO);

        UserResponseDTO result = userUseCase.getCurrentUser(jwt);

        assertNotNull(result);
        assertEquals(mockedResponseDTO, result);
        verify(jwtTokenProvider).getUsernameFromToken(jwt);
        verify(userPort).findByUsername(username);
        verify(modelMapper).map(mockedUser, UserResponseDTO.class);
    }

    @Test
    public void logoutUserTest() {
        String validJwt = "validJWT";
        String invalidJwt = null;

        doNothing().when(jwtTokenProvider).invalidateToken(validJwt);

        MessageResponseDTO validResult = userUseCase.logoutUser(validJwt);

        assertNotNull(validResult);
        assertEquals("User logged out successfully.", validResult.getMessage());
        verify(jwtTokenProvider).invalidateToken(validJwt);

        MessageResponseDTO invalidResult = userUseCase.logoutUser(invalidJwt);

        assertNotNull(invalidResult);
        assertEquals("No JWT token found", invalidResult.getMessage());
    }


}

