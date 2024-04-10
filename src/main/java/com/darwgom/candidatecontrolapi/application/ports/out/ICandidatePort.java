package com.darwgom.candidatecontrolapi.application.ports.out;

import com.darwgom.candidatecontrolapi.domain.models.Candidate;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.MessageResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ICandidatePort {

    Candidate saveCandidate(Candidate candidate);
    Optional<Candidate> findCandidateById(Long id);
    List<Candidate> findAllCandidates();
    Candidate updateCandidate(Candidate candidate);
    void deleteCandidate(Long id);

}
