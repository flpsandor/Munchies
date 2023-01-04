package com.example.munchies.exception;

public class OrderWithRestaurantException extends Exception{
    public OrderWithRestaurantException(String message, RuntimeException e) {
        super(message);
    }
}
