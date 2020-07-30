package concurrent.counter;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * 利用原始数据类型，实现的计数器
 * Created by Niki on 2018/6/20 8:05
 */
public class CompactCounter {
    private volatile long counter;

    private static final AtomicLongFieldUpdater<CompactCounter> updater = AtomicLongFieldUpdater.newUpdater(CompactCounter.class, "counter");

    private long increase() {
        return updater.incrementAndGet(this);
    }

    public static void main(String[] args) {
        new CompactCounter().increase();
    }
}
