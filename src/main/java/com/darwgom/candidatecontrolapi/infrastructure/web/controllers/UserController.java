package com.darwgom.candidatecontrolapi.infrastructure.web.controllers;

import com.darwgom.candidatecontrolapi.application.ports.in.IUserUseCase;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.*;
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

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserUseCase userUseCase;

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
}