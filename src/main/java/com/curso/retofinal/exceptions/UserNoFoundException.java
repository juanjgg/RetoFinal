package com.curso.retofinal.exceptions;

public class UserNoFoundException extends RuntimeException{
    public UserNoFoundException(String message) {
        super(message);
    }
}
