package com.darwgom.candidatecontrolapi.infrastructure.persistence.adapters;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.darwgom.candidatecontrolapi.domain.models.Candidate;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.CandidateEntity;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories.CandidateRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CandidateJpaAdapterTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CandidateJpaAdapter candidateJpaAdapter;

    @Test
    public void whenSaveCandidate_thenCandidateIsSaved() {
        Candidate candidate = mock(Candidate.class);
        CandidateEntity candidateEntity = mock(CandidateEntity.class);

        when(modelMapper.map(candidate, CandidateEntity.class)).thenReturn(candidateEntity);
        when(candidateRepository.save(candidateEntity)).thenReturn(candidateEntity);
        when(modelMapper.map(candidateEntity, Candidate.class)).thenReturn(candidate);

        Candidate savedCandidate = candidateJpaAdapter.saveCandidate(candidate);

        assertNotNull(savedCandidate);
        verify(candidateRepository).save(candidateEntity);
        verify(modelMapper).map(candidate, CandidateEntity.class);
        verify(modelMapper).map(candidateEntity, Candidate.class);
    }

    @Test
    public void whenFindCandidateById_thenCandidateIsReturned() {
        Long id = 1L;
        CandidateEntity candidateEntity = mock(CandidateEntity.class);
        Candidate candidate = mock(Candidate.class);
        Optional<CandidateEntity> candidateEntityOptional = Optional.of(candidateEntity);

        when(candidateRepository.findById(id)).thenReturn(candidateEntityOptional);
        when(modelMapper.map(candidateEntity, Candidate.class)).thenReturn(candidate);

        Optional<Candidate> foundCandidate = candidateJpaAdapter.findCandidateById(id);

        assertTrue(foundCandidate.isPresent());
        assertSame(candidate, foundCandidate.get());
        verify(candidateRepository).findById(id);
        verify(modelMapper).map(candidateEntity, Candidate.class);
    }

    @Test
    public void whenFindAllCandidates_thenAllCandidatesAreReturned() {
        CandidateEntity candidateEntity = mock(CandidateEntity.class);
        Candidate candidate = mock(Candidate.class);
        List<CandidateEntity> candidateEntityList = Arrays.asList(candidateEntity);

        when(candidateRepository.findAll()).thenReturn(candidateEntityList);
        when(modelMapper.map(candidateEntity, Candidate.class)).thenReturn(candidate);

        List<Candidate> allCandidates = candidateJpaAdapter.findAllCandidates();

        assertFalse(allCandidates.isEmpty());
        assertSame(candidate, allCandidates.get(0));
        verify(candidateRepository).findAll();
        verify(modelMapper).map(candidateEntity, Candidate.class);
    }

    @Test
    public void whenUpdateCandidate_thenCandidateIsUpdated() {
        Candidate candidate = mock(Candidate.class);
        CandidateEntity candidateEntity = mock(CandidateEntity.class);

        when(modelMapper.map(candidate, CandidateEntity.class)).thenReturn(candidateEntity);
        when(candidateRepository.save(candidateEntity)).thenReturn(candidateEntity);
        when(modelMapper.map(candidateEntity, Candidate.class)).thenReturn(candidate);

        Candidate updatedCandidate = candidateJpaAdapter.updateCandidate(candidate);

        assertNotNull(updatedCandidate);
        verify(candidateRepository).save(candidateEntity);
        verify(modelMapper).map(candidate, CandidateEntity.class);
        verify(modelMapper).map(candidateEntity, Candidate.class);
    }

    @Test
    public void whenDeleteCandidate_thenCandidateIsDeleted() {
        Long id = 1L;
        doNothing().when(candidateRepository).deleteById(id);

        candidateJpaAdapter.deleteCandidate(id);

        verify(candidateRepository).deleteById(id);
    }
}

