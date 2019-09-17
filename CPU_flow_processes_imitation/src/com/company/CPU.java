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
        this.isFreeNow = true; // cause it was just created and has no tasks
        this.processQueue = processQueue;
    }

    @Override
    public void run() {
        int i = 0;
        boolean canContinue;
        while (true) {
            Process process = processQueue.completeOneProcessAndRemoveFromQueue();
            canContinue = (process != null);
            if (!canContinue) {
                System.out.println("The end. All processes completed. Nothing to process");
                break;
            } else {
                System.out.println("process #" + i + "added to Queue");
                System.out.println("next process will start after" + process.getTimeConsumption());
                imitateExecution(); // just sleep
                i++;
            }
        }
    }


    /**
     * generates random value and cause Thread.sleep()
     */
    public void imitateExecution() {
        isFreeNow = false;
        try {
            Thread.sleep(generateProcessingTime(CPU_MIN_PROCESSING_TIME, CPU_MAX_PROCESSING_TIME));
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
     * generate random lifetime for process
     */
    private int generateProcessingTime(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1)) * 100;
    }

    /**
     * is waiting for process
     */
    public boolean isFreeNow() {
        return isFreeNow;
    }

}
