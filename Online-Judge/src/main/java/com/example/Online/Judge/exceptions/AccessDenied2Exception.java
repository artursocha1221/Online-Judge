package com.example.Online.Judge.exceptions;

public class AccessDenied2Exception extends Exception {
    public AccessDenied2Exception(String entityName) {
        super("You are not permitted to send/receive " + entityName + ".");
    }
}