package com.curso.retofinal.domain.repositories;

import com.curso.retofinal.domain.Cashout;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CashoutRepository extends ReactiveMongoRepository<Cashout, Long> {
    Flux<Cashout> findByUserId(Long userId);
}
