package com.darwgom.candidatecontrolapi.application.ports.in;

import com.darwgom.candidatecontrolapi.infrastructure.web.dto.*;

import java.util.List;

public interface IUserUseCase {

    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
    UserResponseDTO getUser(Long id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
    MessageResponseDTO deleteUser(Long id);
    TokenResponseDTO loginUser(LoginRequestDTO loginRequestDTO);
    UserResponseDTO getCurrentUser(String jwt);
    MessageResponseDTO logoutUser(String jwt);

}
