package multithreading;

import java.util.concurrent.locks.Lock;

public abstract class AbstractProductionWithLockThread extends Thread {

    private Lock lock;

    public synchronized void injectRefs(Lock lock) {
        this.lock = lock;
        this.lock.lock();
    }

    @Override
    public void run() {
    }
}