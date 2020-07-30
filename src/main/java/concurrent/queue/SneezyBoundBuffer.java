package concurrent.queue;

/**
 * 通过线程的中断机制，当某一条件满足时，减少等待时间。增加有界队列的响应时效性
 * Created by Niki on 2018/7/24 8:29
 */
public class SneezyBoundBuffer <V> extends BaseBoundedBuffer<V> {
    protected SneezyBoundBuffer(int capacity) {
        super(capacity);
    }

    /**
     * 这个值需要进行调试，确定线程休眠的时间
     */
    private static final long NO_CONDITION = 1000L;

    private static Thread putThread = new Thread();
    private static Thread getThread = new Thread();

    public void put(V v) {
        while (true) {

            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            try {
                Thread.sleep(NO_CONDITION);
            } catch (InterruptedException e) {
                //发生中断，则立即恢复插入操作
                e.printStackTrace();
            }
        }
    }

    public V take()  {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            try {
                Thread.sleep(NO_CONDITION);
            } catch (InterruptedException e) {
                //发生中断，立即回复读取操作
            }

        }
    }

}
