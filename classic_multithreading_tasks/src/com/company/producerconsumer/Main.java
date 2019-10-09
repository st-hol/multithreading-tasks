package com.company.producerconsumer;

public class Main {

    public static final int N_OF_RESOURCES_TOTAL = 5;

    public static void main(String[] args) {

        ResourceHolder resourceHolder = new ResourceHolder();
        Producer producer = new Producer(resourceHolder);
        Consumer consumer = new Consumer(resourceHolder);
        new Thread(producer).start();
        new Thread(consumer).start();

    }
}

class ResourceHolder {
    private int nOfResources = 0;

    public synchronized void get() {
        while (nOfResources < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        nOfResources--;
        System.out.println("-----------------------");
        System.out.println("//Consumer just made -1");
        System.out.println(">>>>>Current resources: " + nOfResources);
        notify();
    }

    public synchronized void put() {
        while (nOfResources >= 3) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        nOfResources++;
        System.out.println("-----------------------");
        System.out.println("//Producer just made +1");
        System.out.println(">>>>>Current resources: " + nOfResources);
        notify();
    }
}

class Producer implements Runnable {

   private ResourceHolder resourceHolder;

    Producer(ResourceHolder resourceHolder) {
        this.resourceHolder = resourceHolder;
    }

    public void run() {
        for (int i = 0; i < Main.N_OF_RESOURCES_TOTAL; i++) {
            resourceHolder.put();
        }
    }
}

class Consumer implements Runnable {

    private ResourceHolder resourceHolder;

    Consumer(ResourceHolder resourceHolder) {
        this.resourceHolder = resourceHolder;
    }

    public void run() {
        for (int i = 0; i < Main.N_OF_RESOURCES_TOTAL; i++) {
            resourceHolder.get();
        }
    }
}









//public class Main {
//
//    private static final int N_OF_THREADS = 5;
//
//    public static void main(String[] args) {
//
//        CommonResource commonResource = new CommonResource();
//        for (int i = 0; i < N_OF_THREADS; i++) {
//
//            Thread t = new Thread(new CountThread(commonResource));
//            t.setName("Thread #" + i+1);
//            t.start();
//        }
//    }
//}
//
//class CountThread implements Runnable {
//
//    private CommonResource res;
//
//    CountThread(CommonResource res) {
//        this.res = res;
//    }
//
//    public void run() {
//        res.increment();
//    }
//}
//
//class CommonResource {
//
//    private int x;
//    private static final int INCREMENT_LIMIT = 5;
//
//    synchronized void increment() {
//        x = 0;
//        for (int i = 0; i < INCREMENT_LIMIT; i++) {
//            System.out.printf("%s has res = %d; \n", Thread.currentThread().getName(), x);
//            x++;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                //e.printStackTrace();
//                System.out.println(Thread.currentThread().getName() + " has been interrupted");
//                System.out.println(Thread.currentThread().isInterrupted());    // false
//                Thread.currentThread().interrupt();
//            }
//        }
//        System.out.println("____________________________________________");
//    }
//}

