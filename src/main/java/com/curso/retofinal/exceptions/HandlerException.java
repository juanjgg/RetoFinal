package com.curso.retofinal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler({InsufficientBalanceException.class, PaymentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<String>> handleBadRequestException(InsufficientBalanceException exception){
        return Mono.just(ResponseEntity.badRequest().body(exception.getMessage()));
    }

    @ExceptionHandler({UserNoFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleNoFoundException(UserNoFoundException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    @ExceptionHandler({TransactionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleNotFoundException(TransactionNotFoundException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }
}
