package com.pap.demo.exceptions;

/**
 * Exceção personalizada para recursos não encontrados.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
