package com.company.svetofor;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.company.svetofor.Main.generateRandomBounded;
import static com.company.svetofor.Main.swapColors;


enum LightColor {
    GREEN,
    RED
}

public class Main {
    /**
     * generates random
     */
    public static int generateRandomBounded(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    public static void swapColors(LightColor currentLight, Map<LightColor, Boolean> colors){
        if (currentLight.equals(LightColor.GREEN)) {
            colors.put(LightColor.GREEN, true);
            colors.put(LightColor.RED, false);
        } else {
            colors.put(LightColor.GREEN, false);
            colors.put(LightColor.RED, true);
        }
    }

    private static final int N_THREADS = 2;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);

        Exchanger<LightColor> lightColorExchanger = new Exchanger<>();
        executor.execute(new FirstTrafficLight(lightColorExchanger));
        executor.execute(new SecondTrafficLight(lightColorExchanger));
    }
}

class FirstTrafficLight implements Runnable {

    private Exchanger<LightColor> lightColorExchanger;
    private LightColor currentLight;
    private EnumMap<LightColor, Boolean> colors = new EnumMap<>(LightColor.class);

    FirstTrafficLight(Exchanger<LightColor> ex) {
        this.lightColorExchanger = ex;
        currentLight = LightColor.GREEN;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep( generateRandomBounded(500, 2000));
                currentLight = lightColorExchanger.exchange(currentLight);
                swapColors(currentLight, colors);
                System.out.println("FirstTrafficLight colors: " + colors.toString() + " now");
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

class SecondTrafficLight implements Runnable {

    private Exchanger<LightColor> exchanger;
    private LightColor currentLight;
    private EnumMap<LightColor, Boolean> colors = new EnumMap<>(LightColor.class);

    SecondTrafficLight(Exchanger<LightColor> ex) {
        this.exchanger = ex;
        currentLight = LightColor.RED;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(generateRandomBounded(500, 2000));
                currentLight = exchanger.exchange(currentLight);
                swapColors(currentLight, colors);
                System.out.println("SecondTrafficLight colors: " + colors.toString() + " now");
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}


//enum LightColor {
//    GREEN,
//    RED
//}
//
//public class Main {
//    /**
//     * generates random
//     */
//    public static int generateProcessingTime(int min, int max) {
//        return (int) (min + Math.random() * (max - min + 1));
//    }
//
//    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        Exchanger<LightColor> ex = new Exchanger<>();
//        executor.execute(new FirstTrafficLight(ex));
//        executor.execute(new SecondTrafficLight(ex));
//    }
//}
//
//class FirstTrafficLight implements Runnable{
//
//    Exchanger<LightColor> exchanger;
//    LightColor currentLight;
//
//    FirstTrafficLight(Exchanger<LightColor> ex){
//        this.exchanger=ex;
//        currentLight = LightColor.GREEN;
//    }
//    public void run() {
//        while (true) {
//            try {
//                int waitTime = generateProcessingTime(500,2000);
//                Thread.sleep(waitTime);
//                currentLight = exchanger.exchange(currentLight);
//                System.out.println("FirstTrafficLight color is: " + currentLight + " now");
//            } catch (InterruptedException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }
//}
//class SecondTrafficLight implements Runnable{
//
//    Exchanger<LightColor> exchanger;
//    LightColor currentLight;
//
//    SecondTrafficLight(Exchanger<LightColor> ex){
//        this.exchanger=ex;
//        currentLight = LightColor.RED;
//    }
//    public void run() {
//
//        while (true) {
//            try {
//                int waitTime = generateProcessingTime(500,2000);
//                Thread.sleep(waitTime);
//                currentLight = exchanger.exchange(currentLight);
//                System.out.println("SecondTrafficLight color is: " + currentLight + " now");
//            } catch (InterruptedException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }
//}
//
//


//
//enum LastActive {
//    FIRST,
//    SECOND;
//}
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Context context = new Context();
//        FirstTrafficLight firstTrafficLight = new FirstTrafficLight(context);
//        SecondTrafficLight secondTrafficLight = new SecondTrafficLight(context);
//        new Thread(firstTrafficLight).start();
//        new Thread(secondTrafficLight).start();
//
//    }
//}
//
//class Context {
//    private AtomicInteger lastActive = new AtomicInteger(1);
//
//    public synchronized void enableFirst() {
//        while (true) {
//            if (lastActive.get() == 2) {
//                try {
//                    System.out.println(Thread.currentThread().getName() + ": 1 waiting");
//                    wait();
//                    Thread.sleep(new Random().nextInt(1000));
//                } catch (InterruptedException e) {
//                    break;
//                }
//            }
//        }
//        lastActive.set(1);
//        System.out.println(Thread.currentThread().getName() + "-> 1 enabled");
//        notify();
//    }
//
//    public synchronized void enableSecond() {
//        while (true) {
//            if (lastActive.get() == 1) {
//                try {
//                    System.out.println(Thread.currentThread().getName() + ": 2 waiting");
//                    wait();
//                    Thread.sleep(new Random().nextInt(1000));
//                } catch (InterruptedException e) {
//                    break;
//                }
//            }
//        }
//        lastActive.set(2);
//        System.out.println(Thread.currentThread().getName() + "-> 2 enabled");
//        notify();
//    }
//}
//
//
//class FirstTrafficLight implements Runnable {
//
//    private Context context;
//
//    FirstTrafficLight(Context context) {
//        this.context = context;
//    }
//
//    public void run() {
//        while (true) {
//            context.enableFirst();
//        }
//    }
//}
//
//class SecondTrafficLight implements Runnable {
//
//    private Context context;
//
//    SecondTrafficLight(Context context) {
//        this.context = context;
//    }
//
//    public void run() {
//        while (true) {
//            context.enableSecond();
//        }
//    }
//}
