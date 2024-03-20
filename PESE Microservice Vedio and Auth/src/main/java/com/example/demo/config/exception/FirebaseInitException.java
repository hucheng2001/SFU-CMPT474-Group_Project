package com.example.demo.config.exception;

public class FirebaseInitException extends Exception{
    public FirebaseInitException(String msg){
        super(msg);
    }
    public FirebaseInitException(String msg, Throwable cause){
        super(msg, cause);
    }
}
