package com.chikacow.pet_project.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public void handingTest() {
        System.out.println("oh no from global");

        //should return a 404 not found page
        //return "sample";

    }

    @ExceptionHandler(IOException.class)
    public void handlingIoex() {
        System.out.println("oh no from input global");

        //should return a 404 not found page
        //return "sample";

    }

}
