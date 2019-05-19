package com.broadway.has.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class WateringRequest {


    private int valveNumber;
    private long runTimeMs;
    private boolean on;
    private String xbeeAddr;

    public String getXbeeAddr() {
        return xbeeAddr;
    }

    public void setXbeeAddr(String xbeeAddr) {
        this.xbeeAddr = xbeeAddr;
    }

    public int getValveNumber() {
        return valveNumber;
    }

    public void setValveNumber(int valveNumber) {
        this.valveNumber = valveNumber;
    }

    public long getRunTimeMs() {
        return runTimeMs;
    }

    public void setRunTimeMs(long runTimeMs) {
        this.runTimeMs = runTimeMs;
    }

    public boolean getOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public static WateringRequest fromJSON(String json)
            throws JsonProcessingException, IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(json, WateringRequest.class);
    }


}
