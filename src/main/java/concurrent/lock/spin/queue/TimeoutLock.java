package concurrent.lock.spin.queue;

import concurrent.lock.spin.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Niki on 2018/8/2 8:52
 */
public class TimeoutLock implements Lock {
    // 声明为静态变量，防止被临时回收
    private static final QNode AVAILABLE = new QNode();

    private AtomicReference<QNode> tail;

    ThreadLocal<QNode> myNode;

    public TimeoutLock(){
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>(){
            @Override
            protected QNode initialValue(){
                return new QNode();
            }
        };
    }



    @Override
    public void lock() {
        QNode node = new QNode();
        myNode.set(node);
        QNode pre = tail.getAndSet(node);
        if (pre != null) {
            // 在前一个节点自旋，当前一个节点是AVAILABLE时，表示获得锁
            while (pre.preNode != AVAILABLE) {

            }
        }
    }

    @Override
    public void unlock() {
        QNode node = myNode.get();

        if (!tail.compareAndSet(node, null)) {
            //还有节点在等待获得锁
            node.preNode = AVAILABLE;
        }
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        boolean isInterrupted = false;
        long startTime = System.currentTimeMillis();
        long duration = TimeUnit.MILLISECONDS.convert(time, unit);
        QNode node = new QNode();
        myNode.set(node);
        //尝试一次获取锁
        QNode pre = tail.getAndSet(node);
        if (pre == null || pre == AVAILABLE) {
            return true;
        }

        //在给定时间内自旋
        while ((System.currentTimeMillis() - startTime < duration) && !isInterrupted) {
            QNode predPreNode = pre.preNode;
            if (predPreNode == AVAILABLE) {
                return true;
            } else if (predPreNode != null) {
                pre = predPreNode;
            }
            if (Thread.interrupted()) {
                isInterrupted = true;
            }
        }

        node.preNode = pre;
        if (isInterrupted) {
            throw new InterruptedException();
        }
        return false;
    }

    public static class QNode {
        volatile QNode preNode;
    }
}
