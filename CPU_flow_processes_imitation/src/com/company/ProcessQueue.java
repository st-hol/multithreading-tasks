package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Resources
 */
public class ProcessQueue {

    public static final int MAX_QUEUE_LENGTH_LIMITATION = 10;


    private Queue<Process> queue = new LinkedList<>();


    /**
     * @return true if added OR false if queue was full;
     */
    public synchronized boolean addOneProcess(Process process) {
        if (queue.size() == MAX_QUEUE_LENGTH_LIMITATION) {
            return false;
        } else {
            queue.add(process);
            return true;
        }
    }


    /**
     * @return removed  OR null if queue was empty;
     */
    public synchronized Process completeOneProcessAndRemoveFromQueue() {
        Process removed;
        try {
            removed = queue.remove();
        } catch (NoSuchElementException e) {
            return null;
        }
        return removed;
    }

    public synchronized int size() {
        return queue.size();
    }


}
