package com.darwgom.candidatecontrolapi.application.usecases;

import com.darwgom.candidatecontrolapi.application.ports.in.ICandidateUseCase;
import com.darwgom.candidatecontrolapi.application.ports.out.ICandidatePort;
import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import com.darwgom.candidatecontrolapi.domain.exceptions.EntityNotFoundException;
import com.darwgom.candidatecontrolapi.domain.models.Candidate;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateResponseDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.MessageResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CandidateUseCase implements ICandidateUseCase {

    @Autowired
    private ICandidatePort candidatePort;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CandidateResponseDTO createCandidate(CandidateRequestDTO candidateRequestDTO) {
        Candidate candidate = modelMapper.map(candidateRequestDTO, Candidate.class);
        candidate.setGender(GenderEnum.valueOf(candidateRequestDTO.getGender().toUpperCase()));
        Candidate createdCandidate = candidatePort.saveCandidate(candidate);
        return modelMapper.map(createdCandidate, CandidateResponseDTO.class);
    }

    @Override
    public CandidateResponseDTO getCandidate(Long id) {
        Candidate candidate = candidatePort.findCandidateById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + id));
        return modelMapper.map(candidate, CandidateResponseDTO.class);
    }

    @Override
    public List<CandidateResponseDTO> getAllCandidates() {
        List<Candidate> candidates = candidatePort.findAllCandidates();
        return candidates.stream()
                .map(candidate -> modelMapper.map(candidate, CandidateResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CandidateResponseDTO updateCandidate(Long id, CandidateRequestDTO candidateRequestDTO) {
        Candidate existingCandidate = candidatePort.findCandidateById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + id));
        modelMapper.map(candidateRequestDTO, existingCandidate);
        existingCandidate.setGender(GenderEnum.valueOf(candidateRequestDTO.getGender().toUpperCase()));
        Candidate updatedCandidate = candidatePort.saveCandidate(existingCandidate);
        return modelMapper.map(updatedCandidate, CandidateResponseDTO.class);
    }

    @Override
    public MessageResponseDTO deleteCandidate(Long id) {
        candidatePort.findCandidateById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + id));
        candidatePort.deleteCandidate(id);
        return new MessageResponseDTO("Candidate deleted successfully");
    }

}