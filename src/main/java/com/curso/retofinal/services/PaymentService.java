package com.curso.retofinal.services;

import com.curso.retofinal.controllers.requests.CashoutRequest;
import com.curso.retofinal.services.interfaces.IPaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService implements IPaymentService {
    private final WebClient webClient;

    public PaymentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Mono<String> processPayment(CashoutRequest request) {
        return webClient.post()
                .uri("/payment")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }
}
