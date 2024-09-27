package com.curso.retofinal.controllers;

import com.curso.retofinal.controllers.requests.CashoutRequest;
import com.curso.retofinal.domain.Cashout;
import com.curso.retofinal.domain.User;
import com.curso.retofinal.exceptions.InsufficientBalanceException;
import com.curso.retofinal.exceptions.UserNoFoundException;
import com.curso.retofinal.services.interfaces.ICashoutService;
import com.curso.retofinal.services.interfaces.IPaymentService;
import com.curso.retofinal.services.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CashoutControllerTest {

    @Mock
    private IUserService userService;

    @Mock
    private IPaymentService paymentService;

    @Mock
    private ICashoutService cashoutService;

    @InjectMocks
    private CashoutController cashoutController;

    private CashoutRequest request;
    private User user;

    @BeforeEach
    void setUp() {
        request = new CashoutRequest();
        request.setUserId(1L);
        request.setAmount(100.0);

        user = new User();
        user.setUserId(1L);
        user.setBalance(200.0);
    }

    @Test
    void createCashout_Success() {
        when(userService.findById(request.getUserId())).thenReturn(Mono.just(user));
        when(userService.save(any(User.class))).thenReturn(Mono.just(user));
        when(paymentService.processPayment(request)).thenReturn(Mono.just("Payment Successful"));
        when(cashoutService.save(any(Cashout.class))).thenReturn(Mono.just(new Cashout(request.getUserId(), request.getAmount())));

        Mono<Cashout> result = cashoutController.createCashout(request);

        StepVerifier.create(result)
                .expectNextMatches(cashout -> cashout.getUserId().equals(request.getUserId()) && cashout.getAmount().equals(request.getAmount()))
                .verifyComplete();
    }

    @Test
    void createCashout_InsufficientBalance() {
        user.setBalance(50.0);
        when(userService.findById(request.getUserId())).thenReturn(Mono.just(user));

        Mono<Cashout> result = cashoutController.createCashout(request);

        StepVerifier.create(result)
                .expectError(InsufficientBalanceException.class)
                .verify();
    }

    @Test
    void createCashout_UserNotFound() {
        when(userService.findById(request.getUserId())).thenReturn(Mono.empty());

        Mono<Cashout> result = cashoutController.createCashout(request);

        StepVerifier.create(result)
                .expectError(UserNoFoundException.class)
                .verify();
    }
}
