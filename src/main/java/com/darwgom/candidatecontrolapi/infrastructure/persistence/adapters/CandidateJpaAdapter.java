package com.darwgom.candidatecontrolapi.infrastructure.persistence.adapters;

import com.darwgom.candidatecontrolapi.application.ports.out.ICandidatePort;
import com.darwgom.candidatecontrolapi.domain.models.Candidate;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.CandidateEntity;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories.CandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CandidateJpaAdapter implements ICandidatePort {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Candidate saveCandidate(Candidate candidate) {
        CandidateEntity candidateEntity = modelMapper.map(candidate, CandidateEntity.class);
        CandidateEntity savedEntity = candidateRepository.save(candidateEntity);
        return modelMapper.map(savedEntity, Candidate.class);
    }

    @Override
    public Optional<Candidate> findCandidateById(Long id) {
        Optional<CandidateEntity> optionalEntity = candidateRepository.findById(id);
        return optionalEntity.map(entity -> modelMapper.map(entity, Candidate.class));
    }

    @Override
    public List<Candidate> findAllCandidates() {
        List<CandidateEntity> entities = candidateRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, Candidate.class))
                .collect(Collectors.toList());
    }

    @Override
    public Candidate updateCandidate(Candidate candidate) {
        CandidateEntity candidateEntity = modelMapper.map(candidate, CandidateEntity.class);
        CandidateEntity updatedEntity = candidateRepository.save(candidateEntity);
        return modelMapper.map(updatedEntity, Candidate.class);
    }

    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

}
