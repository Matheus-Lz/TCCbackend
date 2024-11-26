package com.pap.demo.exceptions;

/**
 * Exceção personalizada para conflitos, como agendamentos com horários sobrepostos.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
