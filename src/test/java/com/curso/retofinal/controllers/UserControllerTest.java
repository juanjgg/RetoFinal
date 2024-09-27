package com.curso.retofinal.controllers;

import com.curso.retofinal.domain.User;
import com.curso.retofinal.exceptions.UserNoFoundException;
import com.curso.retofinal.services.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setBalance(100.0);
    }

    @Test
    void getUserById_Success() {
        when(userService.findById(anyLong())).thenReturn(Mono.just(user));

        Mono<User> result = userController.getUserById(1L);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getUserId().equals(1L) && u.getName().equals("John Doe"))
                .verifyComplete();
    }

    @Test
    void getUserById_UserNotFound() {
        when(userService.findById(anyLong())).thenReturn(Mono.empty());

        Mono<User> result = userController.getUserById(1L);

        StepVerifier.create(result)
                .expectError(UserNoFoundException.class)
                .verify();
    }

    @Test
    void createUser_Success() {
        when(userService.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<User> result = userController.createUser(user);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getUserId().equals(1L) && u.getName().equals("John Doe"))
                .verifyComplete();
    }

    @Test
    void updateUserBalance_Success() {
        when(userService.findById(anyLong())).thenReturn(Mono.just(user));
        when(userService.save(any(User.class))).thenReturn(Mono.just(user));

        Map<String, Double> update = new HashMap<>();
        update.put("amount", 50.0);

        Mono<User> result = userController.updateUserBalance(1L, update);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getBalance().equals(150.0))
                .verifyComplete();
    }
}
