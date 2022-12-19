package com.example.munchies.controller;

import com.example.munchies.exception.GroupOrderNotExistException;
import com.example.munchies.exception.OrderNotValidException;
import com.example.munchies.exception.RestaurantNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GroupOrderNotExistException.class)
    public String handleGroupOrderNotExistException(GroupOrderNotExistException groupOrderNotExistException, Model model){
        model.addAttribute("error", groupOrderNotExistException.getMessage());
        return "400";
    }

    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(OrderNotValidException.class)
    public String handleOrderNotValidException(OrderNotValidException orderNotValidException, Model model){
        model.addAttribute("error", orderNotValidException.getMessage());
        return "410";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantNotExistException.class)
    public String handleRestaurantNotExistException(RestaurantNotExistException restaurantNotExistException, Model model){
        model.addAttribute("error", restaurantNotExistException.getMessage());
        return "400";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalError.class)
    public String handleInternalServerErrorException(InternalError internalError, Model model){
        model.addAttribute("error", internalError.getMessage());
        model.addAttribute("cause", internalError.getCause());
        return "500";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundException() {
        return "404";
    }
}
