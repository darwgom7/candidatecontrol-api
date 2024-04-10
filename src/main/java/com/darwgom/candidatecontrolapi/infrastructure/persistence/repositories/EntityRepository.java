package com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);

}
