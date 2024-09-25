package com.curso.retofinal.controllers;

import com.curso.retofinal.controllers.requests.CashoutRequest;
import com.curso.retofinal.domain.Cashout;
import com.curso.retofinal.exceptions.InsufficientBalanceException;
import com.curso.retofinal.exceptions.UserNoFoundException;
import com.curso.retofinal.services.interfaces.ICashoutService;
import com.curso.retofinal.services.interfaces.IPaymentService;
import com.curso.retofinal.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cashout")
public class CashoutController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private ICashoutService cashoutService;

    @PostMapping
    public Mono<Cashout> createCashout(@RequestBody CashoutRequest request) {
        return userService.findById(request.getUserId())
                .flatMap(user -> {
                    if (user.getBalance() >= request.getAmount()) {
                        user.setBalance(user.getBalance() - request.getAmount());
                        return userService.save(user)
                                .then(paymentService.processPayment(request))
                                .flatMap(paymentResponse -> cashoutService.save(new Cashout(request.getUserId(), request.getAmount())));
                    } else {
                        return Mono.error(new InsufficientBalanceException("Balance insuficiente"));
                    }
                })
                .switchIfEmpty(Mono.error(new UserNoFoundException("User Not Fount")));
    }
}
