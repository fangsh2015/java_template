package data_structure.queue;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 链表实现的阻塞队列的使用demo
 * 当队列满，生产者阻塞；队列空，消费者阻塞
 * * Created by Niki on 2018/1/25 14:21
 */
public class LinkedBlockingQueueDemo {
    private static LinkedBlockingQueue<String> queue;
    private static final int DEF_CAPACITY = 50;

    public static void initQueue(int capacity) {
        if (queue == null) {
            queue = new LinkedBlockingQueue<>(capacity > 0 ? capacity : DEF_CAPACITY);
        }
    }

    public static String getQueue() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putQueue(String msg) {
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Producer implements Runnable {
        private LinkedBlockingQueue<String> queue;
        private String msg;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Producer(LinkedBlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                int i = 0;
                while (true) {
                    queue.put(Thread.currentThread().getName() + "--"+ i++);
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable {
        private LinkedBlockingQueue<String> queue;

        public Consumer(LinkedBlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true){
                List<String> msgs = Lists.newArrayList();
                queue.drainTo(msgs);
                System.out.println(queue.size());
                for(String msg : msgs){
                    System.out.println("消费的消息为："+msg);
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(queue.isEmpty()){
                    break;
                }
            }

        }
    }

    public static void test(){
        initQueue(10);
        Thread pro1= new Thread(new Producer(queue));
        Thread pro2= new Thread(new Producer(queue));
        Thread pro3= new Thread(new Producer(queue));
        Thread pro4= new Thread(new Producer(queue));
        Thread pro5= new Thread(new Producer(queue));

        pro1.start();
        pro2.start();
        pro3.start();
        pro4.start();
        pro5.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread consumer = new Thread(new Consumer(queue));
        consumer.start();
    }

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<String> test = new LinkedBlockingQueue<String>(10);
        test.put("fang");
        test.put("love");
        test.put("word");
        String e = test.take();
        System.out.println(e);
    }
}
