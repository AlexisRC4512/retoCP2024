package com.aramos.retoCP2024.excepcionPersonalizada;

public class CustomInternalException extends RuntimeException {
    public CustomInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}