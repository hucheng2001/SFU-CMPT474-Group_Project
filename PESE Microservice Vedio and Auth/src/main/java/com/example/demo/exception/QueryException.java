package com.example.demo.exception;

public class QueryException extends Exception{
    public QueryException(String msg){
        super(msg);
    }

    public QueryException(){
        super("server internal error, query failed");
    }
}
