package com.curso.retofinal.controllers;

import com.curso.retofinal.controllers.requests.CashoutRequest;
import com.curso.retofinal.exceptions.PaymentException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping
    public Mono<String> paymentStatus(@RequestBody CashoutRequest request){
        if (request.getUserId() % 2 == 0){
            return Mono.just("Not approved");
        }if (request.getUserId() % 3 == 0){
            return Mono.error(new PaymentException("Payment Failed"));
        }
        return Mono.just("Approved");
    }
}
