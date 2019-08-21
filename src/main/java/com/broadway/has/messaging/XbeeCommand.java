package com.broadway.has.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class XbeeCommand {

    private String xbeeAddr;
    private String message;
    private Byte messageType;

    public String getXbeeAddr() {
        return xbeeAddr;
    }

    public void setXbeeAddr(String xbeeAddr) {
        this.xbeeAddr = xbeeAddr;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public static XbeeCommand fromJSON(String json)
            throws JsonProcessingException, IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(json, XbeeCommand.class);
    }

    public String toJson() throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
