package com.company.readerswriters;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

    public static void main(String[] args) {

        ReadWriteBuffer sharedBuffer = new ReadWriteBuffer();

        new Thread(new Reader(111, sharedBuffer)).start();
        new Thread(new Writer(222, sharedBuffer)).start();
        new Thread(new Reader(777, sharedBuffer)).start();
        new Thread(new Reader(111, sharedBuffer)).start();
        new Thread(new Writer(444, sharedBuffer)).start();
    }
}

class ReadWriteBuffer {

    private static final Lock readWriteLock = new ReentrantLock();
    private Condition readWriteAvailableCondition = readWriteLock.newCondition();

    private int numberReaders = 0;
    private int numberWriters = 0;
    private int numberWriteRequests = 0;

    public void write() throws InterruptedException {

        readWriteLock.lock();

        numberWriteRequests++;
        while (numberReaders > 0) {
            readWriteAvailableCondition.await();
        }

        numberWriteRequests--;
        numberWriters++;
        System.out.println("Writer #" + Thread.currentThread().getId() + " started writing.");
        Thread.sleep(2000);
        System.out.println("Writer #" + Thread.currentThread().getId() + " finished writing.");
        numberWriters--;

        readWriteAvailableCondition.signalAll();
        readWriteLock.unlock();

    }

    public void read() throws InterruptedException {

        readWriteLock.lock();
        while (numberWriters > 0 || numberWriteRequests > 0) {
            readWriteAvailableCondition.await();
        }

        numberReaders++;
        System.out.println("Reader #" + Thread.currentThread().getId() + " started reading.");
        Thread.sleep(500);
        System.out.println("Reader #" + Thread.currentThread().getId() + " finished reading.");
        numberReaders--;

        readWriteAvailableCondition.signalAll();
        readWriteLock.unlock();
    }
}

class Reader implements Runnable {

    private final ReadWriteBuffer buffer;

    private int operatingTime;

    public Reader(int operatingTime, ReadWriteBuffer buffer) {
        this.operatingTime = operatingTime;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(operatingTime);
                buffer.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Writer implements Runnable {

    private final ReadWriteBuffer buffer;

    private int operatingTime;

    public Writer(int operatingTime, ReadWriteBuffer buffer) {
        this.operatingTime = operatingTime;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(operatingTime);
                buffer.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

















//https://codereview.stackexchange.com/questions/59928/readers-writers-problem-using-wait-notify
