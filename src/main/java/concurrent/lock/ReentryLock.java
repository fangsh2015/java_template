package concurrent.lock;

/**
 * 可重入锁
 * Created by Niki on 2018/5/18 16:23
 */
public class ReentryLock {
    private boolean isLock = false;
    private Object lockBy;
    int lockedCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        while (isLock && lockBy != thread) {
            wait();
        }
        isLock = true;
        lockBy = thread ;
        lockedCount++ ;
    }

    public synchronized void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == lockBy) {
            lockedCount-- ;
            if (lockedCount == 0) {
                notify();
                isLock = false;
            }
        }
    }
}
