package com.broadway.has.xbee;

import com.broadway.has.messaging.WateringRequest;
import com.broadway.has.messaging.XbeeCommand;
import com.broadway.has.proto.FlowCommand;
import com.broadway.has.repositories.CommandRunHistoryRepository;
import com.broadway.has.sqs.JmsConfig;
import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee64BitAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XbeeDevice implements XbeeProxy{

    private static final Logger log = LoggerFactory.getLogger(XbeeDevice.class);

    private XBeeDevice xbee;

    @Autowired
    private XBeeListener listener;


    private static final String USB_PORT = "/dev/ttyUSB0";
    private static final int BAUD_RATE = 9600;
    private static boolean isDeviceOpen = false;
    private static boolean isDeviceCreated = false;


    public XbeeDevice(){

        log.info("Opening xbee device....");

        while(!isDeviceCreated){
            log.info("Attempting to create xbee device on {} at {} baud", USB_PORT, BAUD_RATE);
            try{
                xbee = new XBeeDevice(USB_PORT, BAUD_RATE);
                log.debug("XBee device has been created on {} at {} baud", USB_PORT, BAUD_RATE);
                isDeviceCreated = true;
            }catch (Exception e){
                log.error("Failed to create xbee device on {} at {}: {}.  Will retry.", USB_PORT, BAUD_RATE, e);
            }
        }




    }

    public void start(){

        while(!isDeviceOpen){
            log.info("Attempting to open created xbee device on {} at {} baud", USB_PORT, BAUD_RATE);
            try{
                xbee.open();
                log.debug("XBee device has been opened on {} at {} baud", USB_PORT, BAUD_RATE);
                isDeviceOpen = true;
            }catch (Exception e){
                log.error("Failed to open xbee device on {} at {}: {}.  Will retry.", USB_PORT, BAUD_RATE, e);
            }
        }

        try {
            this.xbee.addDataListener(this.listener);
        }catch (Exception e){
            log.error("Exception while xbee was open: {}", e);
        }
    }


    @Override
    public void proxyCommand(XbeeCommand cmd) throws XBeeException{

        //push the command to the xbee
        byte[] msgBytes = cmd.getMessage().getBytes();

        log.info("Executing command {} on XBEE: {}", msgBytes[0], msgBytes);

        RemoteXBeeDevice remoteXBeeDevice = new RemoteXBeeDevice(xbee, new XBee64BitAddress(cmd.getXbeeAddr()));


        byte[] msg = new byte[msgBytes.length + 1];
        msg[0] = cmd.getMessageType();
        System.arraycopy(msgBytes, 0, msg, 1, msgBytes.length);

        //This method can throw, and if it does the caller needs to handle the exception.
        xbee.sendData(remoteXBeeDevice, msg);



        
    }
}
