package concurrent.queue;

/**
 * 有界队列不满足插入和获取的前提情况时，抛异常处理策略
 * 14-3。该方法抛出了无法插入和读取的异常，将问题抛给了方法的调用方
 * Created by Niki on 2018/7/19 8:56
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }

    private static class BufferFullException extends Exception {
    }

    private static class BufferEmptyException extends Exception {
    }

    public static void main(String[] args) throws InterruptedException {
        GrumpyBoundedBuffer buffer = new GrumpyBoundedBuffer(10);

        long time = 1000;

        while (true) {
            try {
                buffer.put(new Object());
                break;
            } catch (BufferFullException e) {
                Thread.sleep(time);
            }
        }

        while (true) {
            try {
                buffer.take();
                break;
            } catch (BufferEmptyException e) {
                Thread.sleep(time);
            }
        }
    }

}
