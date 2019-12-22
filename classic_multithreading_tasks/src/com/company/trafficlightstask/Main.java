package com.company.trafficlightstask;


import java.util.EnumMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int N_THREADS = 2;
    private static final String FIRST = "FirstTrafficLight";
    private static final String SECOND = "SecondTrafficLight";

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
        Semaphore sem = new Semaphore(1);
        TrafficLightService service = new TrafficLightService();

        executor.execute(new TrafficLight(sem, FIRST, new EnumMap<>(TrafficLightColor.class),
                service));
        executor.execute(new TrafficLight(sem, SECOND, new EnumMap<>(TrafficLightColor.class),
                service));
    }
}


//
//public class Main {
//
//    private static final int N_THREADS = 2;
//    private static final String FIRST = "FirstTrafficLight";
//    private static final String SECOND = "SecondTrafficLight";
//
//    public static void main(String[] args) {
//
//        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
//
//    }
//}
//
//class LightThread implements Runnable {
//
//    private AtomicBoolean lastActive;
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                if (lastActive.get()) {
//                    Thread.sleep(1000);
//                    System.out.println("I AM LIGHTING");
//                } else {
//                    wait();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//}
//
//class Scheduler {
//    private AtomicBoolean lastActive;
//    private LightThread lightThread1;
//    private LightThread lightThread2;
//
//    public Scheduler(AtomicBoolean lastActive, LightThread lightThread1, LightThread lightThread2) {
//        this.lastActive = lastActive;
//        this.lightThread1 = lightThread1;
//        this.lightThread2 = lightThread2;
//    }
//
//    public void go() {
//        while (true) {
//            lightThread2.activate();
//            lightThread1.activate();
//        }
//    }
//}
//
//}
