package com.bookstore.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }

}
