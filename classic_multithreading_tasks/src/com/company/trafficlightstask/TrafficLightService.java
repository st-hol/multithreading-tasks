package com.company.trafficlightstask;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrafficLightService {
    /**
     * generates random
     */
    public int generateRandomBounded(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    /**
     * triggers color change of traffic light
     */
    public void swapColors(TrafficLightColor currentLight, Map<TrafficLightColor, Boolean> colors) {
        if (currentLight.equals(TrafficLightColor.GREEN)) {
            colors.put(TrafficLightColor.GREEN, true);
            colors.put(TrafficLightColor.RED, false);
        } else {
            colors.put(TrafficLightColor.GREEN, false);
            colors.put(TrafficLightColor.RED, true);
        }
        log.info("colors switched;");
    }
}
