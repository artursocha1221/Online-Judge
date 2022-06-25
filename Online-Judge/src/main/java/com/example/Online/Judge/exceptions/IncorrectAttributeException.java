package com.example.Online.Judge.exceptions;

public class IncorrectAttributeException extends Exception {
    public IncorrectAttributeException(String message) {
        super(message);
    }

    public IncorrectAttributeException(String attributeName, String wrongName) {
        this(attributeName + " " + wrongName + " is incorrect.");
    }
}