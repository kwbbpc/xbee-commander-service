package com.broadway.has.xbee;

import com.broadway.has.sensor.SensorDao;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class XBeeListener implements IDataReceiveListener {

    private static final Logger logger = LoggerFactory.getLogger(XBeeListener.class);

    @Value("${messaging.name}")
    private String name;



    public void dataReceived(XBeeMessage xBeeMessage) {


        logger.info("Got a new xbee message!");
        try {

            SensorDao sensor = new SensorDao(xBeeMessage.getDevice());

            byte[] rawData = xBeeMessage.getData();
            int messageId = rawData[0];
            byte[] payload = Arrays.copyOfRange(rawData, 1, rawData.length);

            logger.debug("Publishing message to sns queue");

            //this.jms.

            //this.dataHandler.publishMessage(sensor, messageId, payload);

        }catch (Exception e){
            logger.error("Error: " + e.getMessage());
        }

        logger.debug("Data Received!");
        logger.debug("Device: " + xBeeMessage.getDevice().get16BitAddress());
        logger.debug("Data: " + xBeeMessage.getDataString());
        logger.debug("Data: " + xBeeMessage.getData());
    }
}
