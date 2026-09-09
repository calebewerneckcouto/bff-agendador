package com.javanauta.bff_agendador.infrastructure.exception;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message) {
        super(message);
    }


    public IllegalArgumentException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
