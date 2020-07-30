package concurrent.lock.aqs;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock使用Demo
 * jdk1.8提供了StampedLock，用来优化ReentrantReadWriteLock的读写锁
 * StampedLock优化读基于假设，大多数情况下读操作并不会和写操作冲突，
 * 其逻辑是先试着修改，然后通过validate方法确认是否进入写模式，如果没进入，就成功避免了开销；
 * 如果进入，则尝试获取读锁
 * Created by Niki on 2018/6/25 8:36
 */
public class StampedLockDemo {
    private final StampedLock lock = new StampedLock();

    void mutate() {
        long stamp = lock.writeLock();
        try {
            write();
        }finally {
            lock.unlockWrite(stamp);
        }
    }

    private void write() {
        throw new RuntimeException("待实现！");
    }

    String access() {
        long stamp = lock.tryOptimisticRead();
        String data = read();
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                data = read();
            }finally {
                lock.unlockRead(stamp);
            }

        }
        return data;
    }

    private String read() {
        throw new RuntimeException("待实现");
    }
}
