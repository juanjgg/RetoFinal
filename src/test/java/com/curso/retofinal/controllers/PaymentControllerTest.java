package com.curso.retofinal.controllers;

import com.curso.retofinal.controllers.requests.CashoutRequest;
import com.curso.retofinal.exceptions.PaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    private CashoutRequest request;

    @BeforeEach
    void setUp() {
        request = new CashoutRequest();
    }

    @Test
    void paymentStatus_NotApproved() {
        request.setUserId(2L);

        Mono<String> result = paymentController.paymentStatus(request);

        StepVerifier.create(result)
                .expectNext("Not approved")
                .verifyComplete();
    }

    @Test
    void paymentStatus_PaymentFailed() {
        request.setUserId(3L);

        Mono<String> result = paymentController.paymentStatus(request);

        StepVerifier.create(result)
                .expectError(PaymentException.class)
                .verify();
    }

    @Test
    void paymentStatus_Approved() {
        request.setUserId(5L);

        Mono<String> result = paymentController.paymentStatus(request);

        StepVerifier.create(result)
                .expectNext("Approved")
                .verifyComplete();
    }
}
