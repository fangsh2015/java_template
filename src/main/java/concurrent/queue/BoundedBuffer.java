package concurrent.queue;

/**
 * 方法利用Object的通知机制 wait， notifyAll来实现有界队列。兼顾了响应时间，以及CPU效率
 * Created by Niki on 2018/7/19 9:12
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    protected BoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
