package com.company;


/**
 * consumer
 */
public class CPU implements Runnable {

    private static final int CPU_MIN_PROCESSING_TIME = 1;
    private static final int CPU_MAX_PROCESSING_TIME = 10;

    private ProcessQueue processQueue;
    private volatile boolean isFreeNow;

    public CPU(ProcessQueue processQueue) {
        bootUpCPU(100);
        this.processQueue = processQueue;
    }

    @Override
    public void run() {
        boolean canContinue;
        while (true) {
            Process process = processQueue.completeOneProcessAndRemoveFromQueue();
            canContinue = (process != null);
            if (!canContinue) {
                System.out.println("\n[CPU log]: The end. All processes completed. Queue is empty. Nothing to process.");
                break;
            } else {
                imitateExecution(process); // just sleep
                System.out.println("\t[CPU log]: " + process.getName() + " >>> was executed!\n");
            }
        }
    }


    /**
     * generates random value and cause Thread.sleep()
     */
    public void imitateExecution(Process process) {
        System.out.println();
        isFreeNow = false;
        try {
            int executionTime = generateProcessingTime(CPU_MIN_PROCESSING_TIME, CPU_MAX_PROCESSING_TIME);
            System.out.println("\t[CPU log]: " + process.getName() + " >>> will take " + executionTime + " seconds to proceed");
            Thread.sleep(executionTime);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " has been interrupted");
            System.out.println(Thread.currentThread().isInterrupted());    // false
            Thread.currentThread().interrupt();
        } finally {
            isFreeNow = true;
        }
    }


    /**
     * take some time to load. that guarantees that Queue will not be empty when CPU starts
     */
    public void bootUpCPU(int bootingTime) {
        isFreeNow = false;
        try {
            Thread.sleep(bootingTime);
            System.out.println("\n[CPU log]: CPU is booting up!");
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " has been interrupted");
            System.out.println(Thread.currentThread().isInterrupted());    // false
            Thread.currentThread().interrupt();
        } finally {
            isFreeNow = true; // cause it was just created and has no tasks
        }
    }

    /**
     * generate random lifetime for process
     */
    private int generateProcessingTime(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1)) * 15;
    }

    /**
     * is waiting for process
     */
    public boolean isFreeNow() {
        return isFreeNow;
    }

}
