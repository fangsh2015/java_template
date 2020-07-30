package concurrent.lock.spin.queue;

import concurrent.lock.spin.Lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 无界队列锁，使用一个链表来组织线程
 * Created by Niki on 2018/8/1 9:28
 */
public class CLHLock implements Lock {

    private AtomicReference<QNode> tail;

    ThreadLocal<QNode> myNode;
    ThreadLocal<QNode> myPreNode;

    public CLHLock() {
        tail = new AtomicReference<>(new QNode());

        myNode = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return new QNode();
            }
        };

        myPreNode = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {

        QNode node = myNode.get();
        node.lock = true;
        // cas原子操作，保证原子性
        QNode preNOde = tail.getAndSet(node);
        myPreNode.set(preNOde);

        //volatile 变量能保证锁释放及时通知
        //只对前一个节点的状态自旋，减少一致性缓存流量
        while (preNOde.lock) {

        }
    }

    @Override
    public void unlock() {
        QNode node = myNode.get();
        node.lock = false;

        // 把myNode执行preNode， 目的是保证同一个线程下次还能使用这个锁，因为myNode原来指向的节点有它的后一个节点的preNode引用
        // 防止这个线程下次Lock时myNode.get获得原来节点
        myNode.set(myPreNode.get());

    }

    static class QNode {
        volatile boolean lock;
    }

}
