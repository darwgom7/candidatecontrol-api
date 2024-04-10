package com.darwgom.candidatecontrolapi.infrastructure.web.controllers;

import com.darwgom.candidatecontrolapi.application.ports.in.IUserUseCase;
import com.darwgom.candidatecontrolapi.infrastructure.security.JwtTokenProvider;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.UserRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.UserResponseDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.LoginRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.TokenResponseDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.MessageResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserUseCase userUseCase;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userUseCase.registerUser(userRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        TokenResponseDTO responseDTO = userUseCase.loginUser(loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        UserResponseDTO responseDTO = userUseCase.getUser(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> responseDTOs = userUseCase.getAllUsers();
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userUseCase.updateUser(id, userRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteUser(@PathVariable Long id) {
        MessageResponseDTO responseDTO = userUseCase.deleteUser(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(@RequestHeader("Authorization") String jwtHeader) {
        UserResponseDTO responseDTO = userUseCase.getCurrentUser(jwtHeader.replace("Bearer ", ""));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDTO> logoutUser(@RequestHeader("Authorization") String jwtHeader) {
        MessageResponseDTO responseDTO = userUseCase.logoutUser(jwtHeader.replace("Bearer ", ""));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}