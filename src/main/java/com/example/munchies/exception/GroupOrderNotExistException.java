package com.example.munchies.exception;


public class GroupOrderNotExistException extends Exception {

    public GroupOrderNotExistException(String message, Throwable cause){
        super(message, cause);
    }
}
