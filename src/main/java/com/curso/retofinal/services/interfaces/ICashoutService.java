package com.curso.retofinal.services.interfaces;

import com.curso.retofinal.domain.Cashout;
import reactor.core.publisher.Mono;

public interface ICashoutService {
    Mono<Cashout> save(Cashout cashout);
}
