package com.curso.retofinal.controllers;

import com.curso.retofinal.domain.Cashout;
import com.curso.retofinal.domain.repositories.CashoutRepository;
import com.curso.retofinal.exceptions.TransactionNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction-history")
public class TransactionHistory {
   private final CashoutRepository cashoutRepository;

    public TransactionHistory(CashoutRepository cashoutRepository) {
        this.cashoutRepository = cashoutRepository;
    }

    @GetMapping("/user/{id}")
    public Flux<Cashout> getUserById(@PathVariable Long id) {
        return cashoutRepository.findByUserId(id).switchIfEmpty(Mono.error(new TransactionNotFoundException("Transaction by User: " + id + " not Found")));
    }
}
