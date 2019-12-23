package com.company.svetofor;

import java.util.EnumMap;
import java.util.concurrent.Exchanger;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrafficLight implements Runnable{
    private String name;
    private LightColor currentLight;
    private Exchanger<LightColor> lightColorExchanger;
    private EnumMap<LightColor, Boolean> colors;
    private TrafficLightService service;

    public TrafficLight(String name, LightColor currentLight, Exchanger<LightColor> lightColorExchanger,
                        EnumMap<LightColor, Boolean> colors, TrafficLightService service) {
        this.name = name;
        this.currentLight = currentLight;
        this.lightColorExchanger = lightColorExchanger;
        this.colors = colors;
        this.service = service;
    }


    public void run() {
        while (true) {
            try {
                Thread.sleep(service.generateRandomBounded(500, 2000));
                currentLight = lightColorExchanger.exchange(currentLight);
                service.swapColors(currentLight, colors);
                log.info(name + " colors: " + colors.toString() + " now");
            } catch (InterruptedException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}




