package com.company.dinningphilosophers;

import java.util.concurrent.Semaphore;

/**
 * Есть несколько философов, допустим, пять, но одновременно за столом могут сидеть не более двух.
 * И надо, чтобы все философы пообедали, но при этом не возникло взаимоблокировки философами друг друга в борьбе за
 * тарелку и вилку
 */
public class Main {
    private static final int N_OF_PHILOSOPHERS_CAN_EAT_AT_SAME_TIME = 2;

    public static void main(String[] args) {

        Semaphore sem = new Semaphore(N_OF_PHILOSOPHERS_CAN_EAT_AT_SAME_TIME);
        for (int i = 0; i < 5; i++)
            new Philosopher(sem, i + 1).start();
    }
}


class Philosopher extends Thread {

    public static final int MAX_NUMBER_OF_MEALS = 3;

    private Semaphore sem;
    private int numberOfMeals = 0;
    private int id;

    Philosopher(Semaphore sem, int id) {
        this.sem = sem;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (numberOfMeals < MAX_NUMBER_OF_MEALS) {
                sem.acquire();

                System.out.println("Philosopher #" + id + " seats for table.");
                sleep(500); // imitate eating
                numberOfMeals++;

                System.out.println("Philosopher #" + id + " goes out for a walk.");
                sem.release();

                sleep(500); // imitate walking
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " has been interrupted");
            System.out.println(Thread.currentThread().isInterrupted());    // false
            Thread.currentThread().interrupt();
        }
    }
}