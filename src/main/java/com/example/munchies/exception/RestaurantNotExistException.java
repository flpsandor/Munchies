package com.example.munchies.exception;

public class RestaurantNotExistException extends Exception {

    public RestaurantNotExistException(String message, Throwable cause){
        super(message, cause);
    }
}
