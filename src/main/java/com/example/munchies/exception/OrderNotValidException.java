package com.example.munchies.exception;

public class OrderNotValidException extends Exception {
    public OrderNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
