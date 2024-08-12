package com.example.core_demo.exception;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(Long id) {
        super("Request with id " + id + " not found");
    }
}
