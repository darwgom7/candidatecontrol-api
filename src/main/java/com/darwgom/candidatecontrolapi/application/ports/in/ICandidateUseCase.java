package com.darwgom.candidatecontrolapi.application.ports.in;

import com.darwgom.candidatecontrolapi.domain.models.Candidate;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateResponseDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.MessageResponseDTO;

import java.util.List;

public interface ICandidateUseCase {

    CandidateResponseDTO createCandidate(CandidateRequestDTO candidateRequestDTO);
    CandidateResponseDTO getCandidate(Long id);
    List<CandidateResponseDTO> getAllCandidates();
    CandidateResponseDTO updateCandidate(Long id, CandidateRequestDTO candidateRequestDTO);
    MessageResponseDTO deleteCandidate(Long id);

}
