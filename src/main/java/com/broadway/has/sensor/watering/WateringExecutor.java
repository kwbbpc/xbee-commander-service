package com.broadway.has.sensor.watering;

import com.broadway.has.exceptions.XbeeCommunicationsError;
import com.broadway.has.messaging.WateringRequest;
import com.broadway.has.messaging.XbeeCommand;
import com.broadway.has.proto.FlowCommand;
import com.broadway.has.repositories.CommandRunHistoryRepository;
import com.broadway.has.repositories.converters.WateringRequestToCommandHistory;
import com.broadway.has.xbee.MessagingConfigProps;
import com.broadway.has.xbee.XbeeProxy;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WateringExecutor {

    @Autowired
    private XbeeProxy xbeeProxy;

    @Autowired
    private MessagingConfigProps props;

    @Autowired
    private CommandRunHistoryRepository runHistoryRepository;


    public void executeWateringRequest(WateringRequest request) throws XbeeCommunicationsError {
        //convert
        FlowCommand.FlowCommandMessage msg = request.toFlowCommand();
        //serialize
        byte[] msgSerialized = msg.toByteArray();

        XbeeCommand cmd = new XbeeCommand();
        cmd.setMessage(new String(msgSerialized));
        cmd.setXbeeAddr(request.getXbeeAddr());
        cmd.setMessageType((byte)props.getWatering());

        try {
            xbeeProxy.proxyCommand(cmd);

            runHistoryRepository.save(WateringRequestToCommandHistory.convert(request, DateTime.now().toDate(), true));

        }catch (Exception e){

            runHistoryRepository.save(WateringRequestToCommandHistory.convert(request,
                    DateTime.now().toDate(), false, e.getMessage()));
            throw new XbeeCommunicationsError(e.getMessage());

        }


    }
}
