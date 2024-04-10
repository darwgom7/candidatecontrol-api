package com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories;

import com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.CandidateEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends EntityRepository<CandidateEntity, Long> {
}
