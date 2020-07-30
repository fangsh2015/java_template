package concurrent;

import org.apache.tools.ant.taskdefs.Sleep;

/**
 * 使用wait和notifyAll来实现可重新关闭的阀门
 * 并发编程14-9
 * Created by Niki on 2018/8/11 10:50
 */
public class ThreadGate {
    private boolean isOpen;
    private int generation;

    /**
     * 关闭阀门
     */
    public synchronized void close() {
        isOpen = false;
    }

    /**
     * 打开阀门
     */
    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    /**
     * 没有达到阀门条件，阻塞
     * @throws InterruptedException
     */
    public synchronized void await() throws InterruptedException {
        //使用generatino记录打开阀门的次数。用来支持多次打开阀门
        int arrivalgeneration = generation;
        while (!isOpen && arrivalgeneration == generation && test()) {
            wait();
        }
    }



    public boolean test() {
        System.out.println("test方法");
        return true;
    }
}
