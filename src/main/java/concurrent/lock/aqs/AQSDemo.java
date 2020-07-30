package concurrent.lock.aqs;


import javax.swing.plaf.TreeUI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS AbstractQueuedSynchronizer
 * Created by Niki on 2018/6/8 8:02
 */
public class AQSDemo extends AbstractQueuedSynchronizer {

    /** 锁定线程 */
    @Override
    protected boolean tryAcquire(int arg) {
        return compareAndSetState(0, 1);
    }

    /** 释放线程 */
    @Override
    protected boolean tryRelease(int arg) {
        setState(0);
        return true;
    }

    /** 锁是否被占用 */
    @Override
    protected boolean isHeldExclusively() {
        return getState() == 1;
    }

    public void lock() {
        this.acquire(1);
    }

    public void unlock() {
        this.release(1);
    }

}
