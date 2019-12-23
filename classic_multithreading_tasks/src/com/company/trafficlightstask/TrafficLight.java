package com.company.trafficlightstask;

import java.util.EnumMap;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrafficLight implements Runnable {

    private Semaphore sem;
    private String name;
    private EnumMap<TrafficLightColor, Boolean> colors;
    private TrafficLightService service;

    public TrafficLight(Semaphore sem, String name,
                        EnumMap<TrafficLightColor, Boolean> colors,
                        TrafficLightService service) {
        this.sem = sem;
        this.name = name;
        this.colors = colors;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            while (true) {
                log.info(name + " is asking to set GREEN on");
                sem.acquire();
                service.swapColors(TrafficLightColor.GREEN, colors);
                log.info(name + " just turned GREEN on!!!" + " STATE: " + colors.toString());
                Thread.sleep(service.generateRandomBounded(1000, 2000)); // this time is GREEN color state
                service.swapColors(TrafficLightColor.RED, colors);
                log.info(name + " is turning to RED..." + " STATE: " + colors.toString());
                sem.release();

                Thread.sleep(service.generateRandomBounded(2000, 4000)); // this time is RED color state
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            log.info(Thread.currentThread().getName() + " has been interrupted");
            log.info(String.valueOf(Thread.currentThread().isInterrupted()));    // false
            Thread.currentThread().interrupt();
        }
    }

}