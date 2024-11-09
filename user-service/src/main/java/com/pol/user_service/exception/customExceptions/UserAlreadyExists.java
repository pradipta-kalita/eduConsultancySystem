package com.pol.user_service.exception.customExceptions;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String msg) {
        super(msg);
    }
}
