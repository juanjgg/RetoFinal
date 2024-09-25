package com.curso.retofinal.controllers;

import com.curso.retofinal.domain.User;
import com.curso.retofinal.domain.repositories.UserRepository;
import com.curso.retofinal.exceptions.UserNoFoundException;
import com.curso.retofinal.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserService userService;

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userService.findById(id).switchIfEmpty(Mono.error(new UserNoFoundException("User Not Fount")));
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}/balance")
    public Mono<User> updateUserBalance(@PathVariable Long id, @RequestBody Map<String, Double> update) {
        return userService.findById(id)
                .flatMap(user -> {
                    user.setBalance(user.getBalance() + update.get("amount"));
                    return userService.save(user);
                });
    }
}
