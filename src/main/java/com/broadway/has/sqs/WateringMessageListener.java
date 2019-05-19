package com.broadway.has.sqs;


import com.broadway.has.messaging.WateringRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

@Service
public class WateringMessageListener {

    private Logger log = LoggerFactory.getLogger(WateringMessageListener.class);

    @Autowired
    private com.broadway.has.xbee.XbeeProxy XbeeProxy;


    //JMS Listener marks this method as the target of a JMS message listener.  Method def will be consumed by the processor
    // for the enableJms annotation earlier.  When a message is recieved, this method is invoked.
    @JmsListener(destination = "xbee-commands")
    public void handleWateringRequest(String requestJSON) throws JMSException {
        log.info("Received watering request {}", requestJSON);
        try {
            WateringRequest request = WateringRequest.fromJSON(requestJSON);

            //proxy this request to the XBEE
            XbeeProxy.handleWateringRequest(request);

        }catch (Exception e){
            log.error("Error processing request: {}.  Exception: {}", requestJSON, e);
        }
    }

}
