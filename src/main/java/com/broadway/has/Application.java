package com.broadway.has;

import com.broadway.has.sensor.watering.WateringExecutor;
import com.broadway.has.xbee.MessagingConfigProps;
import com.broadway.has.xbee.XbeeDevice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@EnableConfigurationProperties(MessagingConfigProps.class)
public class Application{

    static ApplicationContext context;

    public static void main(String[] args){

        context = SpringApplication.run(Application.class, args);

        WateringExecutor e = context.getBean(WateringExecutor.class);
        e.executeWateringRequest(null);
        //XbeeDevice dev = context.getBean(XbeeDevice.class);
        //dev.start();

    }
}




