package concurrent.lock.spin.queue;

import concurrent.lock.spin.Lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 在无cache的NUMA系统架构中的自旋锁
 * 无界队列，使用一个链表来组织线程
 * 与CLHLock最大的不同是，该锁用于NUMA系统中，该系统没有缓存，每个处理器CPU与自己的主内存通信，并且访问自己内存效率远大于访问其他节点内存
 * 所有该锁自旋的对象不是其他节点，而是自己节点的状态。并且自己维护与下一个节点的关系。
 * Created by Niki on 2018/8/2 8:29
 */
public class MCSLock implements Lock {
    private AtomicReference<QNode> tail;

    // 两个指针，一个纸箱自己的Node, 一个指向前一个Node
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        QNode node = myNode.get();
        // CAS原子操作，保证原子性
        QNode preNode = tail.getAndSet(node);
        if (preNode != null) {
            node.lock = true;
            node.next = preNode;
            //在线程自己的node上进行自旋，对无cache的NUMA系统架构来说，访问本地内存速度由于其他节点内存
            while (node.lock) {

            }
        }
    }

    @Override
    public void unlock() {
        QNode node = myNode.get();
        if (node.next == null) {
            // CAS 操作，判断是否以后新加入的节点
            if (tail.compareAndSet(node, null)) {
                //没有新加入的节点
                return;
            }

            //有新加入的节点，等待设置链关系
            while (node.next == null) {

            }
        }
        //通知下一个节点获取锁
        node.next.lock = false;
        //设置next节点为空，为下次节点获取锁清理状态
        node.next = null;

    }

    public static class QNode {
        volatile boolean lock;
        volatile QNode next;
    }
}
