package concurrent.thread.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * apache线程池GenericObjectPool
 * Created by Niki on 2018/6/11 9:02
 */
public class ThreadPoolDemo2 {

    public void test() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(20));
        pool.execute(() -> System.out.println("小鱼儿"));

    }
}
