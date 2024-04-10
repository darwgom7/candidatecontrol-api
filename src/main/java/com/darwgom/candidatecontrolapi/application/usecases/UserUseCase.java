package com.darwgom.candidatecontrolapi.application.usecases;

import com.darwgom.candidatecontrolapi.application.ports.in.IUserUseCase;
import com.darwgom.candidatecontrolapi.application.ports.out.IUserPort;
import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import com.darwgom.candidatecontrolapi.domain.exceptions.EntityNotFoundException;
import com.darwgom.candidatecontrolapi.domain.exceptions.IllegalParamException;
import com.darwgom.candidatecontrolapi.domain.exceptions.ValueAlreadyExistsException;
import com.darwgom.candidatecontrolapi.domain.models.User;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.MessageResponseDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.UserRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserUseCase implements IUserUseCase {

    @Autowired
    private IUserPort userPort;

    @Autowired
    private ModelMapper modelMapper;

    private static final Map<String, String> TYPE_NORMALIZATION_MAP = Map.of(
            "ROLE_ADMIN", "ROLE_ADMIN",
            "ROLEADMIN", "ROLE_ADMIN",
            "ADMIN", "ROLE_ADMIN",
            "ROLE_CANDIDATE", "ROLE_CANDIDATE",
            "ROLECANDIDATE", "ROLE_CANDIDATE",
            "CANDIDATE", "ROLE_CANDIDATE"
    );

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        if (userPort.existsByUsername(userRequestDTO.getUsername())) {
            throw new ValueAlreadyExistsException("User already exists!");
        }

        String normalizedType = normalizeRoleType(userRequestDTO.getRole());
        userRequestDTO.setRole(normalizedType);


        //userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User user = modelMapper.map(userRequestDTO, User.class);
        User savedUser = userPort.saveUser(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUser(Long id) {
        User user = userPort.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userPort.findAllUsers();
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userPort.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (!existingUser.getUsername().equals(userRequestDTO.getUsername()) && userPort.existsByUsername(userRequestDTO.getUsername())) {
            throw new ValueAlreadyExistsException("User already exists!");
        }

        modelMapper.map(userRequestDTO, existingUser);
        String normalizedType = normalizeRoleType(userRequestDTO.getRole());
        userRequestDTO.setRole(normalizedType);
        existingUser.setRole(RoleEnum.valueOf(userRequestDTO.getRole().toUpperCase()));
        User updatedUser = userPort.saveUser(existingUser);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    @Override
    public MessageResponseDTO deleteUser(Long id) {
        userPort.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userPort.deleteUser(id);
        return new MessageResponseDTO("User deleted successfully");
    }

    private String normalizeRoleType(String type) {
        if (type == null) {
            throw new IllegalParamException("Role type cannot be null");
        }
        String normalizedType = TYPE_NORMALIZATION_MAP.get(type.toUpperCase());
        if (normalizedType == null) {
            throw new IllegalParamException("Invalid role type: " + type);
        }
        return normalizedType;
    }

}