package concurrent.lock.aqs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 1. 公平性
 * 2. 超时
 * 3. 共享
 * 4. 可重入
 * Created by Niki on 2018/6/30 10:45
 */
public class AQSSelfDemo {
    private volatile boolean lock = false;
    //加锁
    public synchronized void lock() throws InterruptedException {
        Thread currThread = Thread.currentThread();
        if (lock) {
            wait();
        }
        lock = true;
        System.out.println(currThread.getName() + "获得锁！");
    }

    //锁释放
    public synchronized void unlock() {
        Thread currThread = Thread.currentThread();
        notify();
        System.out.println(currThread.getName() + "释放锁！");
        lock = false;
    }

    //超时
    public synchronized void lock(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
        if (lock) {
            Future future = new FutureTask(() -> {
                wait();
                return "";
            });
            future.get(timeout, unit);
        }
        lock = true;
    }

    //公平性队列
    private volatile List<Thread> lockThreads = Collections.synchronizedList(new LinkedList<Thread>());

    public synchronized void lockFair() throws InterruptedException {
        Thread currThread = Thread.currentThread();
        if (lock) {
            lockThreads.add(currThread);
            //第一次wait
            wait();
            Thread firstLock = lockThreads.get(0);
            if (firstLock != currThread) {
                System.out.println(currThread.getName() + "还没有资格获得锁");
                wait();
            }
            lockThreads.remove(0);
        }
        System.out.println(currThread.getName() + "获得锁！");
        lock = true;
    }

    //锁可被共享的线程数
    private int share = 3;
    private List<Thread> shareThreads = new ArrayList<>(share);
    public synchronized void lockShare(int share) throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        if (shareThreads.size() == share) {
            //达到共享线程数，并且线程不在已获得锁列表中
            if (!shareThreads.contains(currentThread)) {
                wait();
            }
        }else{
            shareThreads.add(currentThread);
        }
    }

    public synchronized void unLockShare() {
        Thread currentThread = Thread.currentThread();
        if (shareThreads.contains(currentThread)) {
            notify();
            shareThreads.remove(currentThread);
        }
    }

    public static void main(String[] args) {
    }
}
