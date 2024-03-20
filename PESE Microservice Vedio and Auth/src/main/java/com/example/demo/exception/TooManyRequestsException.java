package com.example.demo.exception;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }

    public TooManyRequestsException() {
        super("The request is frequent, please try again later");
    }
}
