package concurrent.lock;

import java.util.concurrent.locks.Condition;

/**
 * Created by Niki on 2018/8/13 9:37
 */
public class SemaphoreOnLock {
//    private final java.util.concurrent.locks.ReentrantLock lock = new java.util.concurrent.locks.ReentrantLock();
//
//    private final Condition permitsAvailable = lock.newCondition();
//
//    private int permits;
//
//    SemaphoreOnLock(int permits) {
//        lock.lock();
//        try {
//
//            this.permits = permits;
//        }finally {
//            lock.unlock();
//        }
//    }
//
//    public void acquire() throws InterruptedException {
//        lock.lock();
//        try {
//            while (permits <= 0) {
//                permitsAvailable.await();
//                --permits;
//            }
//        }finally {
//            lock.unlock();
//        }
//    }
//
//    public void release() {
//        lock.lock();
//        try {
//            ++permits;
//            permitsAvailable.signal();
//        }finally {
//            lock.unlock();
//        }
//    }
}
