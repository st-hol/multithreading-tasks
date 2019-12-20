package com.company.svetofor;

import java.util.Map;

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
    public void swapColors(LightColor currentLight, Map<LightColor, Boolean> colors){
        if (currentLight.equals(LightColor.GREEN)) {
            colors.put(LightColor.GREEN, true);
            colors.put(LightColor.RED, false);
        } else {
            colors.put(LightColor.GREEN, false);
            colors.put(LightColor.RED, true);
        }
    }
}
