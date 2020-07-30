package concurrent.lock.spin.queue;

import concurrent.lock.spin.Lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于数组实现的有界队列锁。主要解决自旋锁如下问题
 * 1.大量缓存一致性流量（线程在一个共享变量上旋转），每个线程在不同的变量上自旋等待锁。
 * 但是有个疑问，无论在多少个变量上进行自旋，那么在总线上进行的信号量不会因为分散到不同的变量而减少，儿什么会少减少了缓存一致性流浪
 * 提供一个可能的解释，假设CPU核心数为多个，那么如果有多个自旋变量，那么就会将缓存一致性流量分散，从而减少一个CPU上的缓存一致性流量带来的影响
 * 2.不能及时获取锁释放信息，导致获取锁时间变长，及时响应性差
 * 3.不能保证无饥饿，有线程无法获取锁
 *
 * 使用volatile数组来组织线程
 * 缺点是得先预知线程规模n，所有线程获取同一个锁的次数不能超过n
 * 假设L把锁，那么锁的空间复杂度O(Ln)
 * Created by Niki on 2018/7/25 8:31
 */
public class ArrayLock implements Lock {
    // 使用volatile数组来存放锁标志，flag[i]=true,表示可以获得锁
    private volatile boolean[] flags;

    private AtomicInteger tail;

    private final int capacity;

    private ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public ArrayLock(int capacity) {
        this.capacity = capacity;
        this.flags = new boolean[capacity];
        this.tail = new AtomicInteger(0);
        flags[0] = true;
    }

    @Override
    public void lock() {
        int slot = tail.getAndIncrement() % capacity;
        mySlotIndex.set(slot);
        //flag[slot]==true表示获得了锁，volatile变量保证锁释放及时通知
        //等待锁的线程只需要在对应的slot上进行自旋判断。
        while (!flags[slot]) {

        }
    }

    @Override
    public void unlock() {
        int slot = mySlotIndex.get();
        flags[slot] = false;
        flags[(slot + 1) % capacity] = true;
    }

    @Override
    public String toString() {
        return "ArrayLock{}";
    }
}
