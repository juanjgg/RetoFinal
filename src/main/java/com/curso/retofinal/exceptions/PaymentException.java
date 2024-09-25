package com.curso.retofinal.exceptions;

public class PaymentException extends RuntimeException{
    public PaymentException(String message) {
        super(message);
    }
}
