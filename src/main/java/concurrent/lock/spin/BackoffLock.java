package concurrent.lock.spin;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 采用sleep使线程休眠带来的负面影响则是，影响线程的响应性。可能无法及时响应锁空闲的情况
 * Created by Niki on 2018/7/20 9:05
 */
public class BackoffLock implements Lock {
    private final int minDelay, maxDelay;
    private int limit;
    final Random random;

    private AtomicBoolean lock = new AtomicBoolean(false);

    public BackoffLock(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        random = new Random();
    }

    @Override
    public void lock() {
        while (true) {
            //锁的状态是加锁情况，不对锁进行修改，自旋等待。减少了一致性流量
            while (!lock.get()) {

            }
            //进行CAS加锁，如果失败，则进行一段时间休眠（回退），避免大量使用CPU资源
            if (!lock.getAndSet(true)) {
                return;
            }else {
                //没有竞争到锁资源则进行休眠，避免占用大量的CPU计算资源
                try {
                    backoff();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unlock() {

    }

    public void backoff() throws InterruptedException {
        int delay = random.nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(delay);
    }
}
