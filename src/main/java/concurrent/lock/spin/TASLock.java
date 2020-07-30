package concurrent.lock.spin;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自旋锁的一种实现
 * 这种方式会一直对锁状态进行修改，大量消耗CPU的计算资源，
 * 并且在锁竞争激烈的情况下，并造成大量的一致性流量，影响其他缓存的正常读写
 * Created by Niki on 2018/7/20 8:42
 */
public class TASLock implements Lock {
    /**
     * 锁标记
     */
    private volatile AtomicBoolean lock = new AtomicBoolean(false);

    @Override
    public void lock() {
        /**
         * 将lock的值设为true，表示加锁。并且返回之前的值。
         * 如果之前的值为true，则表示之前所已经被其他线程获取。则一直自旋等待
         */
        while (lock.getAndSet(true)) {

        }
    }

    @Override
    public void unlock() {
        lock.set(false);
    }
}
