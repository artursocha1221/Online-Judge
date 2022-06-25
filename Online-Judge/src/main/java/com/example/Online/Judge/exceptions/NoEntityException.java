package com.example.Online.Judge.exceptions;

public class NoEntityException extends Exception {
    public NoEntityException(String message) {
        super(message);
    }

    public NoEntityException(String entityName, Long id) {
        this(entityName + " with id = " + id + " doesn't exist.");
    }
}