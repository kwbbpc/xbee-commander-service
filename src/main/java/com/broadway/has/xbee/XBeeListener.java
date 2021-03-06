package com.broadway.has.xbee;

import com.broadway.has.messaging.XbeeCommand;
import com.broadway.has.sensor.SensorDao;
import com.broadway.has.sqs.JmsConfig;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class XBeeListener implements IDataReceiveListener {

    private static final Logger logger = LoggerFactory.getLogger(XBeeListener.class);


    @Autowired
    private ApplicationContext cxt;



    public void dataReceived(XBeeMessage xBeeMessage) {


        logger.info("Got a new xbee message!");
        try {

            SensorDao sensor = new SensorDao(xBeeMessage.getDevice());

            byte[] rawData = xBeeMessage.getData();
            byte messageId = rawData[0];
            byte[] payload = Arrays.copyOfRange(rawData, 1, rawData.length);

            XbeeCommand cmd = new XbeeCommand();
            cmd.setMessage(new String(payload));
            cmd.setMessageType(messageId);
            cmd.setXbeeAddr(sensor.getAddress64bit());

            logger.debug("Publishing message to sns queue");

            //this.jms.
            JmsTemplate jms = cxt.getBean(JmsConfig.class).defaultJmsTemplate();

            try {
                jms.convertAndSend("xbee-messages", cmd.toJson());
            }catch (Exception e){
                logger.error(e.getMessage());
            }


        }catch (Exception e){
            logger.error("Error: " + e.getMessage());
        }

        logger.debug("Data Received!");
        logger.debug("Device: " + xBeeMessage.getDevice().get16BitAddress());
        logger.debug("Data: " + xBeeMessage.getDataString());
        logger.debug("Data: " + xBeeMessage.getData());
    }
}
