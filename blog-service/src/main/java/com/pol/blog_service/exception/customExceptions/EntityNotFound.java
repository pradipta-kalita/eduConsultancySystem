package com.pol.blog_service.exception.customExceptions;

public class EntityNotFound extends RuntimeException{
    public EntityNotFound(String message){
        super(message);
    }
}
