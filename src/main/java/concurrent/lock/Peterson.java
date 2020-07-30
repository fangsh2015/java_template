package concurrent.lock;

import java.util.concurrent.locks.Lock;

/**
 * Peterson算法，实现的锁
 * 该锁保证两个线程使用锁的时候，锁几倍互斥，无死锁，无饥饿特性
 * 如果n线程无死锁互斥算法，需要n个不同的存储单元（变量）来保存中间状态
 * Created by Niki on 2018/7/17 9:14
 *
 * @author nikifang
 */
public abstract class Peterson implements Lock {
    //存储单元，保存中间状态
    private boolean[] flag = new boolean[2];
    private int victim;

    @Override
    public void lock() {
        int i = getThreadId();
        int j = 1 - i;
        //保证两个线程先后运行部死锁，实现互斥
        flag[i] = true;
        //保证两个线程同时运行时不思索实现互斥
        victim = i;
        //等待
        while (flag[j] && victim == i) {

        }
    }


    @Override
    public void unlock() {
        int i = getThreadId();
        flag[i] = false;
    }

    public abstract int getThreadId();
}
