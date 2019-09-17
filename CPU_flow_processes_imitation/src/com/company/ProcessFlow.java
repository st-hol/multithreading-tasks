package com.company;


/**
 * producer
 */
public class ProcessFlow implements Runnable {

    private ProcessQueue processQueue;

    private int nProcessesSubmittedToExecution;


    public ProcessFlow(ProcessQueue processQueue, int nProcessesSubmittedToExecution) {
        this.processQueue = processQueue;
        this.nProcessesSubmittedToExecution = nProcessesSubmittedToExecution;
    }

    @Override
    public void run() {
        System.out.println();
        Process process;
        for (int i = 0; i < nProcessesSubmittedToExecution; i++) {
            process = new Process("process #" + i);
            boolean addedSuccessfully = processQueue.addOneProcess(process);
            if (!addedSuccessfully){
                System.out.println("process #" + i + "was not added to Queue cause it was full");
            }
            System.out.println("process #" + i + "added to Queue");
            System.out.println("next process will start after" + process.getTimeConsumption());
            makePauseBeforeCreatingNext(process);
        }
    }




    /**
     * just pause according to task
     */
    public void makePauseBeforeCreatingNext(Process process) {
        try {
            Thread.sleep(process.getTimeConsumption());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " has been interrupted");
            System.out.println(Thread.currentThread().isInterrupted());    // false
            Thread.currentThread().interrupt();
        }
    }

}
