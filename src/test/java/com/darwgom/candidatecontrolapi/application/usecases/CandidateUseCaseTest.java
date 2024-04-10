package com.darwgom.candidatecontrolapi.application.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.darwgom.candidatecontrolapi.application.ports.out.ICandidatePort;
import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import com.darwgom.candidatecontrolapi.domain.models.Candidate;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateRequestDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.CandidateResponseDTO;
import com.darwgom.candidatecontrolapi.infrastructure.web.dto.MessageResponseDTO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CandidateUseCaseTest {

    @Mock
    private ICandidatePort candidatePort;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CandidateUseCase candidateUseCase;

    @Test
    public void whenCreateCandidate_thenCandidateIsCreated() {
        CandidateRequestDTO requestDTO = mock(CandidateRequestDTO.class);
        Candidate candidate = mock(Candidate.class);
        Candidate createdCandidate = mock(Candidate.class);
        CandidateResponseDTO responseDTO = mock(CandidateResponseDTO.class);

        when(requestDTO.getGender()).thenReturn("MALE");
        when(modelMapper.map(requestDTO, Candidate.class)).thenReturn(candidate);
        when(candidatePort.saveCandidate(candidate)).thenReturn(createdCandidate);
        when(modelMapper.map(createdCandidate, CandidateResponseDTO.class)).thenReturn(responseDTO);

        CandidateResponseDTO result = candidateUseCase.createCandidate(requestDTO);

        assertNotNull(result);
        verify(candidatePort).saveCandidate(candidate);
        verify(modelMapper).map(requestDTO, Candidate.class);
        verify(modelMapper).map(createdCandidate, CandidateResponseDTO.class);
        verify(requestDTO).getGender();
    }


    @Test
    public void whenGetCandidate_thenCandidateIsReturned() {
        Long id = 1L;
        Candidate candidate = mock(Candidate.class);
        CandidateResponseDTO responseDTO = mock(CandidateResponseDTO.class);

        when(candidatePort.findCandidateById(id)).thenReturn(Optional.of(candidate));
        when(modelMapper.map(candidate, CandidateResponseDTO.class)).thenReturn(responseDTO);

        CandidateResponseDTO result = candidateUseCase.getCandidate(id);

        assertNotNull(result);
        verify(candidatePort).findCandidateById(id);
        verify(modelMapper).map(candidate, CandidateResponseDTO.class);
    }

    @Test
    public void whenGetAllCandidates_thenAllCandidatesAreReturned() {
        Candidate candidate = mock(Candidate.class);
        List<Candidate> candidateList = Collections.singletonList(candidate);
        CandidateResponseDTO responseDTO = mock(CandidateResponseDTO.class);

        when(candidatePort.findAllCandidates()).thenReturn(candidateList);
        when(modelMapper.map(candidate, CandidateResponseDTO.class)).thenReturn(responseDTO);

        List<CandidateResponseDTO> result = candidateUseCase.getAllCandidates();

        assertFalse(result.isEmpty());
        verify(candidatePort).findAllCandidates();
        verify(modelMapper, atLeastOnce()).map(candidate, CandidateResponseDTO.class);
    }

    @Test
    public void whenUpdateCandidate_thenCandidateIsUpdated() {
        Long id = 1L;
        CandidateRequestDTO requestDTO = mock(CandidateRequestDTO.class);
        Candidate existingCandidate = new Candidate();
        Candidate updatedCandidate = new Candidate();
        CandidateResponseDTO responseDTO = new CandidateResponseDTO();

        when(requestDTO.getGender()).thenReturn("MALE");
        when(candidatePort.findCandidateById(id)).thenReturn(Optional.of(existingCandidate));
        when(candidatePort.saveCandidate(any(Candidate.class))).thenReturn(updatedCandidate);

        doAnswer(invocation -> {
            CandidateRequestDTO dto = invocation.getArgument(0);
            Candidate candidate = invocation.getArgument(1);
            candidate.setGender(GenderEnum.valueOf(dto.getGender().toUpperCase()));
            return null;
        }).when(modelMapper).map(any(CandidateRequestDTO.class), any(Candidate.class));

        when(modelMapper.map(updatedCandidate, CandidateResponseDTO.class)).thenReturn(responseDTO);

        CandidateResponseDTO result = candidateUseCase.updateCandidate(id, requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(candidatePort).findCandidateById(id);
        verify(candidatePort).saveCandidate(existingCandidate);
        verify(modelMapper).map(requestDTO, existingCandidate);
        verify(modelMapper).map(updatedCandidate, CandidateResponseDTO.class);
    }


    @Test
    public void whenDeleteCandidate_thenCandidateIsDeleted() {
        Long id = 1L;
        Candidate candidate = mock(Candidate.class);

        when(candidatePort.findCandidateById(id)).thenReturn(Optional.of(candidate));
        doNothing().when(candidatePort).deleteCandidate(id);

        MessageResponseDTO result = candidateUseCase.deleteCandidate(id);

        assertNotNull(result);
        assertEquals("Candidate deleted successfully", result.getMessage());
        verify(candidatePort).findCandidateById(id);
        verify(candidatePort).deleteCandidate(id);
    }
}

