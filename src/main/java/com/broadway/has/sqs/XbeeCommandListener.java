package com.broadway.has.sqs;


import com.broadway.has.messaging.XbeeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;



/*This is deprecated in favor of an architecture that doesn't need to know how to convert protobufs.  Later, this class
will be improved to listen for JSON and process those JSON messages into a protobuf message sent to XBEEs.
 */
@Service
public class XbeeCommandListener {

    private Logger log = LoggerFactory.getLogger(XbeeCommandListener.class);

    @Autowired
    private com.broadway.has.xbee.XbeeProxy XbeeProxy;


    //JMS Listener marks this method as the target of a JMS message listener.  Method def will be consumed by the processor
    // for the enableJms annotation earlier.  When a message is recieved, this method is invoked.
    @JmsListener(destination = "xbee-commands")
    public void processXbeeCommand(String message) throws JMSException {
        log.info("Received xbee command:", message);
        try {

            XbeeCommand cmd = XbeeCommand.fromJSON(message);

            log.info("Parsed xbee command: {}", cmd);

            //proxy this request to the XBEE
            XbeeProxy.proxyCommand(cmd);

        }catch (Exception e){
            log.error("Error processing request: {}.  Exception: {}", message, e);
        }
    }

}
