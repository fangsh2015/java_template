package test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Niki on 2018/2/26 8:27
 */
public class TestLock {

    static class LockThread implements Runnable{
        private Lock lock ;
        protected LockThread(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                //lock.lock();
                System.out.println("当前线程为："+Thread.currentThread().getName());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //lock.unlock();
            }

        }
    }

}
