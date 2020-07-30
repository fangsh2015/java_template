package concurrent.lock.spin;

import concurrent.lock.spin.queue.CLHLock;

/**
 * 测试锁
 * Created by Niki on 2018/8/1 9:37
 */
public class Main {
    //private static Lock lock = new ArrayLock(150);

    private static Lock lock = new CLHLock();

    //private static TimeCost timeCost = new TimeCost(new TTASLock());

    private static volatile int value = 0;


    public static void main(String[] args) {
        reLock();
    }


    public static void method(){
        lock.lock();
        System.out.println("Value: " + ++value);
        lock.unlock();
    }

    public static void mulThread() {
        for(int i = 0; i < 50; i ++){
            Thread t = new Thread(new Runnable(){

                @Override
                public void run() {
                    method();
                }

            });
            t.start();
        }
    }


    private static void reLock() {
        lock.lock();
        System.out.println("获得锁");
        lock.lock();
        System.out.println("重入获得锁");
        lock.unlock();

    }
}
