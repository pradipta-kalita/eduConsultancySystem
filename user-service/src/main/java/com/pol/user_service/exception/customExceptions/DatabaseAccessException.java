package com.pol.user_service.exception.customExceptions;

public class DatabaseAccessException extends RuntimeException{
    public DatabaseAccessException(String msg){
        super(msg);
    }
}
