package data_structure.queue;

import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Niki on 2018/1/25 19:21
 */
public class ConcurrentLinkedQueueDemo {
    volatile int i ;
    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> demo = new ConcurrentLinkedQueue<>();
        demo.add("fang");
        demo.offer("shu");

    }
}
