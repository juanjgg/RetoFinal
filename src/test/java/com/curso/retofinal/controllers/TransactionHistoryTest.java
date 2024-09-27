package com.curso.retofinal.controllers;

import com.curso.retofinal.domain.Cashout;
import com.curso.retofinal.domain.repositories.CashoutRepository;
import com.curso.retofinal.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionHistoryTest {

    @Mock
    private CashoutRepository cashoutRepository;

    @InjectMocks
    private TransactionHistory transactionHistory;

    private Cashout cashout;

    @BeforeEach
    void setUp() {
        cashout = new Cashout();
        cashout.setUserId(1L);
        cashout.setAmount(100.0);
    }

    @Test
    void getUserById_Success() {
        when(cashoutRepository.findByUserId(anyLong())).thenReturn(Flux.just(cashout));

        Flux<Cashout> result = transactionHistory.getUserById(1L);

        StepVerifier.create(result)
                .expectNextMatches(c -> c.getUserId().equals(1L) && c.getAmount().equals(100.0))
                .verifyComplete();
    }

    @Test
    void getUserById_TransactionNotFound() {
        when(cashoutRepository.findByUserId(anyLong())).thenReturn(Flux.empty());

        Flux<Cashout> result = transactionHistory.getUserById(1L);

        StepVerifier.create(result)
                .expectError(TransactionNotFoundException.class)
                .verify();
    }
}
