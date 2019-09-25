package com.broadway.has.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failure to communicate with XBEE")
public class XbeeCommunicationsError extends  RuntimeException{

    private String error;

    public XbeeCommunicationsError(){}
    public XbeeCommunicationsError(String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
