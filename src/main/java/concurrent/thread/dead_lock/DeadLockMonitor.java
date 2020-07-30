package concurrent.thread.dead_lock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 采用Java提供的api监控死锁的情况
 * Created by Niki on 2018/6/19 8:49
 */
public class DeadLockMonitor {
    public static void main(String[] args) {
        monitor();
    }
    public static void monitor() {
        ThreadMXBean threadMonitor = ManagementFactory.getThreadMXBean();

        long[] threads = threadMonitor.findDeadlockedThreads();
        if (threads != null) {
            ThreadInfo[] infos = threadMonitor.getThreadInfo(threads);
            for (ThreadInfo info : infos) {
                System.out.println("死锁线程为：" + info.getThreadName() + ",等待的锁为：" + info.getLockName());
            }
        }
    }

    public static void startMonitorThread() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            monitor();
        }, 5, 10, TimeUnit.SECONDS);
    }
}
