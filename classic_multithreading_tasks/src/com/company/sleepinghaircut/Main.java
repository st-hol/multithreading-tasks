package com.company.sleepinghaircut;

import static java.lang.Thread.sleep;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static final int CHAIRS_AVAILABLE_IN_WAITING_ROOM = 5;
    private static final int N_OF_CUSTOMERS = 15;

    public static void main(String[] args) {

        Lock barberLock = new ReentrantLock();
        Condition barberIsAvailableCondition = barberLock.newCondition(); //condition to wait if the barber is not available

        AtomicInteger numberOfFreeSeats = new AtomicInteger(CHAIRS_AVAILABLE_IN_WAITING_ROOM);  // if no free seats - don't wait. then LEAVE
        CustomersFlow customersFlow = new CustomersFlow(CHAIRS_AVAILABLE_IN_WAITING_ROOM); // q of waiting customers

        Barber barber = new Barber(customersFlow, barberLock, numberOfFreeSeats, barberIsAvailableCondition);
        barber.start();

        for (int i = 0; i < N_OF_CUSTOMERS; i++) {
            Customer customer = new Customer(i + 1, true, barberIsAvailableCondition, numberOfFreeSeats, customersFlow, barberLock);
            customer.start();
            try {
                sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " has been interrupted");
                System.out.println(Thread.currentThread().isInterrupted());    // false
                Thread.currentThread().interrupt();
            }
        }
    }
}

class CustomersFlow {

    private final Lock lock = new ReentrantLock();
    private final Condition poolAvailable = lock.newCondition();
    private int numCustomers;
    private final int maxNumCustomers;

    public CustomersFlow(int maxNumCustomers) {
        this.maxNumCustomers = maxNumCustomers;
        this.numCustomers = 0;
    }

    public void lookUpForCustomer() throws InterruptedException {
        lock.lock();
        try {
            while (numCustomers <= 0) {
                poolAvailable.await();
            }
            --numCustomers;
        } finally {
            lock.unlock();
        }
    }

    public void processCustomer() {
        lock.lock();
        try {
            // check to ensure release does not occur before acquire
            if (numCustomers >= maxNumCustomers) {
                return;
            }
            ++numCustomers;
            poolAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

}


class Customer extends Thread {

    private int id;
    private boolean isNotHaircuttedYet;
    private Condition barberIsAvailableCondition;
    private AtomicInteger numberOfFreeSeats;
    private CustomersFlow customersFlow;
    private Lock barberLock;

    public Customer(int id, boolean isNotHaircuttedYet, Condition barberIsAvailableCondition,
                    AtomicInteger numberOfFreeSeats, CustomersFlow customersFlow, Lock barberLock) {
        this.id = id;
        this.isNotHaircuttedYet = isNotHaircuttedYet;
        this.barberIsAvailableCondition = barberIsAvailableCondition;
        this.numberOfFreeSeats = numberOfFreeSeats;
        this.customersFlow = customersFlow;
        this.barberLock = barberLock;
    }

    @Override
    public void run() {
        while (isNotHaircuttedYet) {
            if (numberOfFreeSeats.get() > 0) {
                System.out.println("Customer #" + this.id + " just sat down on the waiting chair.");
                numberOfFreeSeats.getAndDecrement();
                customersFlow.processCustomer(); // notify the barber that there is customer
                barberLock.lock();
                try {
                    barberIsAvailableCondition.await();  //barber is cutting another customer
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().getName() + " has been interrupted");
                    System.out.println(Thread.currentThread().isInterrupted());    // false
                    Thread.currentThread().interrupt();
                } finally {
                    barberLock.unlock();
                }
                isNotHaircuttedYet = false;
                getHaircut();
            } else { // there are no free seats
                System.out.println("There are no free seats. Customer #" + id + " has left the barbershop.");
                isNotHaircuttedYet = false;
            }
        }
    }

    /**
     * imitation - just sleep
     */
    public void getHaircut() {
        System.out.println("Customer #" + id + " is getting haircut now!");
        try {
            sleep(2050);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " has been interrupted");
            System.out.println(Thread.currentThread().isInterrupted());    // false
            Thread.currentThread().interrupt();
        }
    }

}


class Barber extends Thread {

    private CustomersFlow customersFlow;
    private Lock barberLock;
    private AtomicInteger numberOfFreeSeats;
    private Condition barberIsAvailableCondition;


    public Barber(CustomersFlow customersFlow, Lock barberLock, AtomicInteger numberOfFreeSeats,
                  Condition barberIsAvailableCondition) {
        this.customersFlow = customersFlow;
        this.barberLock = barberLock;
        this.numberOfFreeSeats = numberOfFreeSeats;
        this.barberIsAvailableCondition = barberIsAvailableCondition;
    }

    @Override
    public void run() {
        while (true) {
            try {
                customersFlow.lookUpForCustomer(); // if no customers - AWAIT
                numberOfFreeSeats.getAndIncrement(); // one chair gets free
                barberLock.lock();
                try {
                    barberIsAvailableCondition.signal(); // stop AWAIT
                } finally {
                    barberLock.unlock();
                }
                cutHair();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " has been interrupted");
                System.out.println(Thread.currentThread().isInterrupted());    // false
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * imitation - just sleep
     */
    public void cutHair() {
        System.out.println("The barber is cutting hair");
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " has been interrupted");
            System.out.println(Thread.currentThread().isInterrupted());    // false
            Thread.currentThread().interrupt();
        }
    }
}