package com.darwgom.candidatecontrolapi.infrastructure.web.controllers;

import com.darwgom.candidatecontrolapi.application.ports.in.ICandidateUseCase;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateResponseDTO;
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

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private ICandidateUseCase candidateUseCase;

    @PostMapping
    public ResponseEntity<CandidateResponseDTO> createCandidate(@RequestBody CandidateRequestDTO candidateRequestDTO) {
        CandidateResponseDTO responseDTO = candidateUseCase.createCandidate(candidateRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidate(@PathVariable Long id) {
        CandidateResponseDTO responseDTO = candidateUseCase.getCandidate(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CandidateResponseDTO>> getAllCandidates() {
        List<CandidateResponseDTO> responseDTOs = candidateUseCase.getAllCandidates();
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> updateCandidate(@PathVariable Long id, @RequestBody CandidateRequestDTO candidateRequestDTO) {
        CandidateResponseDTO responseDTO = candidateUseCase.updateCandidate(id, candidateRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteCandidate(@PathVariable Long id) {
        MessageResponseDTO responseDTO = candidateUseCase.deleteCandidate(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}