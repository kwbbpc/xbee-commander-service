package com.broadway.has.messaging;

import java.util.Date;
import java.util.Map;

public class Request {

    final private Date dateExecuted;
    final private boolean isSendSuccess;
    final private String commandType;

    public Request(Date dateExecuted, boolean isSendSuccess, String commandType) {
        this.dateExecuted = dateExecuted;
        this.isSendSuccess = isSendSuccess;
        this.commandType = commandType;
    }


    public Date getDateExecuted() {
        return dateExecuted;
    }

    public boolean isSendSuccess() {
        return isSendSuccess;
    }

    public String getCommandType() {
        return commandType;
    }

}
