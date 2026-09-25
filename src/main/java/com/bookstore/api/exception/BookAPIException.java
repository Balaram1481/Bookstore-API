package com.bookstore.api.exception;
import org.springframework.http.HttpStatus;

public class BookAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BookAPIException(HttpStatus status,String message){
        this.status=status;
        this.message=message;
    }
    public HttpStatus getStatus(){ return status;}
    @Override 
    public String getMessage(){ return message;}

}
