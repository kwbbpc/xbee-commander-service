package com.broadway.has.xbee;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ConfigurationProperties(prefix = "messaging")
public class ConfigProps {

    private int weather;
    private int motion;
    private int watering;

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public void setMotion(int motion) {
        this.motion = motion;
    }

    public void setWatering(int watering) {
        this.watering = watering;
    }

    public int getWeather() {
        return weather;
    }

    public int getMotion() {
        return motion;
    }

    public int getWatering() {
        return watering;
    }
}
