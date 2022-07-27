package com.example.Online.Judge.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ExceptionHandler<T> {
    private String message;
    private HttpStatus httpStatus;

    public ExceptionHandler(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ResponseEntity<T> handle() {
        System.out.println(message);
        return new ResponseEntity<>(httpStatus);
    }
}
