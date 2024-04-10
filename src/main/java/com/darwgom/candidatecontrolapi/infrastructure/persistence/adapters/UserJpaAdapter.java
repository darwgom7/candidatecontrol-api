package com.darwgom.candidatecontrolapi.infrastructure.persistence.adapters;

import com.darwgom.candidatecontrolapi.application.ports.out.IUserPort;
import com.darwgom.candidatecontrolapi.domain.models.User;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.entities.UserEntity;
import com.darwgom.candidatecontrolapi.infrastructure.persistence.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserJpaAdapter implements IUserPort {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        UserEntity savedEntity = userRepository.save(userEntity);
        return modelMapper.map(savedEntity, User.class);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        return optionalEntity.map(entity -> modelMapper.map(entity, User.class));
    }

    @Override
    public List<User> findAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, User.class))
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(User user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        UserEntity updatedEntity = userRepository.save(userEntity);
        return modelMapper.map(updatedEntity, User.class);
    }

    @Override
    public User findByUsername(String username) {
        Optional<UserEntity> optionalEntity = userRepository.findByUsername(username);
        return optionalEntity.map(entity -> modelMapper.map(entity, User.class)).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}