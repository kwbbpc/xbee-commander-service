package com.broadway.has.repositories.converters;

import com.amazonaws.services.opsworks.model.Command;
import com.broadway.has.messaging.WateringRequest;
import com.broadway.has.repositories.CommandHistoryDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;

public class WateringRequestToCommandHistory {

    public static CommandHistoryDao convert(WateringRequest request, Date executionTime, boolean isSuccessful, String error){

        CommandHistoryDao cmd = new CommandHistoryDao();
        cmd.setCommandType("Watering");
        cmd.setDateExecuted(executionTime);
        cmd.setSendSuccess(isSuccessful);
        cmd.setError(error);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = mapper.convertValue(request, new TypeReference<Map<String,Object>>() {});
        cmd.setJsonObject(json);

        return cmd;

    }

    public static CommandHistoryDao convert(WateringRequest request, Date executionTime, boolean isSuccessful){

        return convert(request, executionTime, isSuccessful, null);

    }
}
