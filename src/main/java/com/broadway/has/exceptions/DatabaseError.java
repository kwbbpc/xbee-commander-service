package com.broadway.has.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "The database encountered an error while processing request.")
public class DatabaseError extends RuntimeException{

    private String message;

    public DatabaseError() {
    }

    public DatabaseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
