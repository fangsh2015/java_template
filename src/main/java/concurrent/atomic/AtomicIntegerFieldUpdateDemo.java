package concurrent.atomic;

import java.util.concurrent.CountDownLatch;
        import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by Niki on 2018/6/22 9:21
 */
public class AtomicIntegerFieldUpdateDemo {
    private volatile int index_unsafe = 0;
    private volatile int index_safe = 0;

    public static void main(String[] args) {
        AtomicIntegerFieldUpdateDemo demo = new AtomicIntegerFieldUpdateDemo();
        demo.unsafeIncr();
        demo.safeIncr();
    }

    private static class Test implements Runnable {
        private AtomicIntegerFieldUpdateDemo demo ;
        CountDownLatch latch;

        public Test(AtomicIntegerFieldUpdateDemo demo, CountDownLatch latch) {
            this.demo = demo;
            this.latch = latch;
        }

        public Test(AtomicIntegerFieldUpdateDemo demo) {
            this.demo = demo;
        }

        @Override
        public void run() {
            demo.index_unsafe ++;
            latch.countDown();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void unsafeIncr() {
        CountDownLatch latch = new CountDownLatch(5000);
        for (int i = 0; i < 5000; i++) {
            new Thread(new Test(this,latch)).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("非线程安全自增值：" + this.index_unsafe);
    }

    private void safeIncr() {
        final AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdateDemo> safe = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdateDemo.class, "index_safe");
        CountDownLatch latch = new CountDownLatch(5000);
        for (int i = 0; i < 5000; i++) {
            new Thread(()->{
                safe.addAndGet(this, 1);
                latch.countDown();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程安全自增值："+safe.get(this));
    }
}
