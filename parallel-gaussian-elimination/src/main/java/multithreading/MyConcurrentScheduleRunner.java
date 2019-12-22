package multithreading;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyConcurrentScheduleRunner {

    private static final Logger LOG = Logger.getLogger(MyConcurrentScheduleRunner.class.getName());

    private final Lock lock = new ReentrantLock();
    private final AbstractQueue<AbstractProductionWithLockThread> list = new ConcurrentLinkedQueue<>();

    public void startAll() {

        Iterator<AbstractProductionWithLockThread> iter = list.iterator();
        while (iter.hasNext()) {
            AbstractProductionWithLockThread p = iter.next();
            runOne(p);
        }
        wakeAll();
        iter = list.iterator();
        while (iter.hasNext()) {
            try {
                AbstractProductionWithLockThread p = iter.next();
                p.join();
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        list.clear();
    }

    public void addThread(AbstractProductionWithLockThread prodThread) {
        list.add(prodThread);
    }

    private void runOne(AbstractProductionWithLockThread prodThread) {
        prodThread.instantiateLockAndApplyImmediately(lock);
        prodThread.start();
    }

    private synchronized void wakeAll() {
        lock.unlock();
    }
}
