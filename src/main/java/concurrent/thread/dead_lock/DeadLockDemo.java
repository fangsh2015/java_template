package concurrent.thread.dead_lock;

import java.lang.management.ManagementFactory;

/**
 * 模拟死锁发生的情况
 * Created by Niki on 2018/6/19 8:40
 */
public class DeadLockDemo implements Runnable {

    private String lockA;
    private String lockB;

    public DeadLockDemo(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println("alread get lock: " + lockA);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB) {
                System.out.println("after get lock:" + lockA + "then get lock :" + lockB);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLockMonitor.startMonitorThread();
        getPid();
        String lockA = "lockA";
        String lockB = "lockB";

        Thread a = new Thread(new DeadLockDemo(lockA, lockB));

        Thread b = new Thread(new DeadLockDemo(lockB, lockA));

        a.start();
        b.start();
        a.join();
        b.join();

    }

    public static void getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
    }

}
