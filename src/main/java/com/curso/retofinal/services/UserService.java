package com.curso.retofinal.services;

import com.curso.retofinal.domain.User;
import com.curso.retofinal.domain.repositories.UserRepository;
import com.curso.retofinal.services.interfaces.IUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<User> findById(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Mono<User> save(User user) {
        return repository.save(user);
    }
}
