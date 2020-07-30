package concurrent.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用读写锁包装Map 实现并发性更高的访问
 * Created by Niki on 2018/8/10 16:10
 */
public class ReadWriteMap<K,V> {
    private final Map<K, V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock read = lock.readLock();
    private final Lock write = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    public V put(K key, V value) {
        write.lock();
        try {
            return map.put(key, value);
        }finally {
            write.unlock();
        }
    }

    public V get(K key) {
        read.lock();
        try {
            return map.get(key);
        }finally {
            read.unlock();
        }
    }
}
