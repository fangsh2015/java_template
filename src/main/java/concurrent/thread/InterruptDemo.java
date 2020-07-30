package concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.taskdefs.Sleep;

/**
 * 线程中断的操作
 * Created by Niki on 2018/6/8 8:55
 */
@Slf4j
public class InterruptDemo {

    public static void main(String[] args) {
        noInterruptMethod();
        System.exit(0);
    }

    private static void haveInterruptMethod() {
        final boolean stop = false;
        Thread t = new Thread(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("发生中断一场！");
                    e.printStackTrace();
                }
            }
        });

        t.start();
        t.interrupt();
        System.out.println(String.format("现场是否存活--> %s", t.isAlive()));
        System.out.println(String.format("现场是否中断--> %s", t.isInterrupted()));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void noInterruptMethod() {
        final int i = 0;
        Thread t = new Thread(() -> {
            for (; ; ) {
                try {

                    System.out.println("test");
                } catch (Exception e) {
                    log.error("发生中断一场！", e);
                }
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
