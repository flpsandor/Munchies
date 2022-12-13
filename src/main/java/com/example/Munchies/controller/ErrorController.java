package com.example.Munchies.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status!=null){
            int statusCode = Integer.parseInt(status.toString());
            if(HttpStatus.NOT_FOUND.value()==statusCode){
                return "404";
            }
            if(HttpStatus.INTERNAL_SERVER_ERROR.value()==statusCode){
                return "500";
            }
        }
        return "error";
    }
}
