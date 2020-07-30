package concurrent.queue;

/**
 * 14-5
 * 这种方式，将条件不满足线程休眠的逻辑内聚到工具类中，对外提供更加友好的接口
 * 但是这种方式带来的影响就是程序的响应性不好。可能在线程刚休眠，条件已经满足，则浪费了等待的休眠时间
 * 为了解决响应性的问题，处理方式有
 * 1. 减少休眠时间，如果满足条件立即获得通知
 * 2. 不使用休眠，满足条件立即执行。采用自旋锁的方式
 * 因为自旋锁的方式会消耗大量的CPU资源，因此通知机制是效率最好的一种方式。
 * Created by Niki on 2018/7/19 9:04
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    /**
     * 这个值需要进行调试，确定线程休眠的时间
     */
    private static final long NO_CONDITION = 1000L;

    public void put(V v) throws InterruptedException {
        while (true) {
            //当插入条件不允许时，释放锁，其他线程得以执行，用以改变某些阻塞的状态。避免某些状态一直得不到改变，而线程不释放锁，导致程序无法继续执行。
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(NO_CONDITION);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(NO_CONDITION);

        }
    }
}
