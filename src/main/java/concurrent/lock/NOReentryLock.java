package concurrent.lock;

/**
 * 不可重入锁
 * Created by Niki on 2018/5/18 16:16
 */
public class NOReentryLock {
    private boolean isLock = false;

    public synchronized void lock() throws InterruptedException {
        while (isLock) {
            //再次获得锁时，则一直等待，知道锁释放
            wait();
        }
        //一开始为false，则获得了锁顺利执行完lock方法
        isLock = true;
    }

    public synchronized void unlock() {
        notify();
        isLock = false;
    }

    public void do_1() throws InterruptedException {
        this.lock();
        System.out.println("方法1");
        do_2();
        this.unlock();
    }

    /*
    如果该方法和do_1方法使用同一个锁，则该方法不会执行，一直阻塞。因为do_1获得了锁，没有释放因此do_2一直无法获得锁。
     */
    public void do_2() throws InterruptedException {
        this.lock();
        System.out.println("重入方法2");
        this.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        NOReentryLock demo = new NOReentryLock();
        demo.do_1();
    }

}
