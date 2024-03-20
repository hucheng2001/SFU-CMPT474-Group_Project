package com.example.demo.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg){
        super(msg);
    }
    public UserNotFoundException(){
    }
}
