package com.broadway.has;

import com.broadway.has.xbee.ConfigProps;
import com.broadway.has.xbee.XbeeDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProps.class)
public class Application{

    static ApplicationContext context;

    public static void main(String[] args){

        context = SpringApplication.run(Application.class, args);

        XbeeDevice dev = context.getBean(XbeeDevice.class);
        dev.start();

    }
}




