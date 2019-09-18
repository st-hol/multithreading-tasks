package com.company;

public class Process {

    private static final int PROCESS_MIN_LIFETIME = 1;
    private static final int PROCESS_MAX_LIFETIME = 10;

    /**
     * time Before Next Process Can Be Generated
     */
    private int timeConsumption;

    private String name;

    public Process(String name) {
        this.timeConsumption = generateLifetime(PROCESS_MIN_LIFETIME, PROCESS_MAX_LIFETIME);
        this.name = name;
    }

    /**
     * generate random lifetime for process
     */
    private int generateLifetime(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1)) * 10;
    }


    public int getTimeConsumption() {
        return timeConsumption;
    }

    public String getName() {
        return name;
    }

}
