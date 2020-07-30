package data_structure.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Niki on 2018/1/25 18:36
 */
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> demo = new ArrayBlockingQueue<String>(10);

        demo.put("fang");
        demo.add("shu");
        demo.offer("hao");

        demo.poll();
        demo.peek();

    }
}
