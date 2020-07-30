package concurrent.lock.spin.queue;

import concurrent.lock.spin.TryLock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by Niki on 2018/8/7 8:58
 */
public class CompositeLock implements TryLock {

    enum State {FREE, WAITING, RELEASED, ABORTED}

    class QNode {
        AtomicReference<State> state = new AtomicReference<>(State.FREE);
        volatile QNode preNode;
    }

    private final int SIZE = 10;

    private final int MIN_BACKOFF = 1;

    private final int MAX_BACKOFF = 10;

    private Random random = new Random();

    private QNode[] waitings = new QNode[10];

    /**
     * 对尾指针，使用AtomicStampedReference带版本号的原子引用变量，防止ABA问题，因为存在同一个Node多次进出队列
     */
    private AtomicStampedReference<QNode> tail = new AtomicStampedReference<>(null, 0);

    private ThreadLocal<QNode> myNOde = new ThreadLocal<QNode>() {
        @Override
        public QNode initialValue() {
            return null;
        }
    };

    public CompositeLock() {
        for (int i = 0; i < SIZE; i++) {
            waitings[i] = new QNode();
        }
    }

    @Override
    public void lock() {
        QNode node = waitings[random.nextInt(SIZE)];

        while (true) {
            while (node.state.get() != State.FREE) {
                int[] currentStamp = new int[1];
                QNode tailNode = tail.get(currentStamp);
                if (tailNode == node && tailNode.state.get() == State.RELEASED) {
                    if (tail.compareAndSet(tailNode, null, currentStamp[0], currentStamp[0] + 1)) {
                        node.state.set(State.WAITING);
                        break;
                    }
                }
            }

            if (node.state.compareAndSet(State.FREE, State.WAITING)) {
                break;
            }
        }
    }

    @Override
    public void unlock() {

    }

    @Override
    public boolean trylock(long time, TimeUnit unit) {
        return false;
    }
}
