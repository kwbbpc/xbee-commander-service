package com.broadway.has.messaging;

import com.broadway.has.proto.FlowCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Date;

public class WateringRequest {


    private int valveNumber;
    private long runTimeMs;
    private boolean on;
    private String xbeeAddr;
    private Request requestDetails;

    public boolean isOn() {
        return on;
    }

    public Request getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(Request requestDetails) {
        this.requestDetails = requestDetails;
    }

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

    public void setOn(boolean on) {
        this.on = on;
    }

    public static WateringRequest fromJSON(String json)
            throws JsonProcessingException, IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(json, WateringRequest.class);
    }

    public FlowCommand.FlowCommandMessage toFlowCommand(){

        FlowCommand.FlowCommandMessage.Builder msg = FlowCommand.FlowCommandMessage.newBuilder();
        msg.setIsOn(this.on);
        msg.setPinNumber(this.valveNumber);
        msg.setRunTimeMs(((Long)this.runTimeMs).intValue());
        return msg.build();

    }


}
