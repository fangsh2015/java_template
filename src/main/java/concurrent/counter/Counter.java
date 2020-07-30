package concurrent.counter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用AtomicLong类实现一个并发安全的计数器
 * Created by Niki on 2018/6/20 8:04
 */
public class Counter {
    private final AtomicLong counter = new AtomicLong();

    public long increase() {
        return counter.incrementAndGet();
    }
}
