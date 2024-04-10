package com.darwgom.candidatecontrolapi.application.ports.out;

import com.darwgom.candidatecontrolapi.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserPort {

    User saveUser(User candidate);
    Optional<User> findUserById(Long id);
    List<User> findAllUsers();
    User updateUser(User candidate);
    User findByUsername(String username);
    void deleteUser(Long id);
    boolean existsByUsername(String username);

}
