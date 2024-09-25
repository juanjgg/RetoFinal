package com.curso.retofinal.services;


import com.curso.retofinal.domain.Cashout;
import com.curso.retofinal.domain.repositories.CashoutRepository;
import com.curso.retofinal.services.interfaces.ICashoutService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CashoutService implements ICashoutService {

    private final CashoutRepository cashoutRepository;

    public CashoutService(CashoutRepository cashoutRepository) {
        this.cashoutRepository = cashoutRepository;
    }

    @Override
    public Mono<Cashout> save(Cashout cashout) {
        return cashoutRepository.save(cashout);
    }
}
