package com.pol.blog_service.exception.customExceptions;

public class BlogNotFound extends RuntimeException{
    public BlogNotFound(String message){
        super(message);
    }
}
