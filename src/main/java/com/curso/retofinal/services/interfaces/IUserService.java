package com.curso.retofinal.services.interfaces;

import com.curso.retofinal.domain.User;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> findById(Long userId);
    Mono<User> save(User user);
}
