package concurrent.lock;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁使用Demo
 * 1. 如果读锁师试图锁定时，写锁被某个线程锁定，则读锁无法获得，等待写操作完成
 * 2. 读锁支持重入。一个读线程获取了读锁之后，其他的读线程依然可以获取读锁
 * Created by Niki on 2018/6/25 8:29
 */
public class RWSample {
    private final Map<String, String> m = new TreeMap<>();
    volatile boolean cacheValida; //缓存是否有效
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLcok = rwLock.writeLock();

    public static void main(String[] args) {
        RWSample sample = new RWSample();
        sample.m.put("key", "恭喜发财");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                sample.get("key");
            }).start();

        }
    }

    public String get(String key) {
        readLock.lock();
        System.out.println(Thread.currentThread().getName() + "获取了读锁！");
        try {
            Thread.sleep(5000);
            return m.get(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "发生异常";
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了读锁！");
            readLock.unlock();
        }
    }

    public String write(String key, String value) {
        writeLcok.lock();
        System.out.println("写锁锁定！");
        try {
            return m.put(key, value);
        } finally {
            writeLcok.unlock();
        }
    }

    public void processCacheData() {
        try {
            readLock.lock();
            //缓存无效，则写缓存
            if (!cacheValida) {
                readLock.unlock();
                writeLcok.lock();
                if (!cacheValida) {
                    m.put("cache", "填充缓存");
                    cacheValida = true;
                    //锁降级在释放写锁之前获取读锁

                    readLock.lock();
                    writeLcok.unlock();
                }
            }
        } finally {
            readLock.unlock();
        }
    }
}
