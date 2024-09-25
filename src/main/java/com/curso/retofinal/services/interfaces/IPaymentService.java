package com.curso.retofinal.services.interfaces;

import com.curso.retofinal.controllers.requests.CashoutRequest;
import reactor.core.publisher.Mono;

public interface IPaymentService {
    Mono<String> processPayment(CashoutRequest cashoutRequest);
}
