package concurrent.lock.spin;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 这个类是TASLock的升级，主要是未了避免大量的CPU计算，以及大量的一致性流量
 * 解决大量消耗CPU计算资源的方法则是让现场休眠，让出CPU时间，供其他线程使用
 * 解决大量一致性流量的方法则是，避免平方的modified，采用监听的方式
 * Created by Niki on 2018/7/20 8:53
 */
public class TTASSLock implements Lock {
    private AtomicBoolean lock = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            //锁的状态是加锁情况，不对锁进行修改，自旋等待。减少了一致性流量
            while (!lock.get()) {

            }
            //进行CAS加锁，如果失败，则进行一段时间休眠（回退），避免大量使用CPU资源
            if (!lock.getAndSet(true)) {
                return;
            }
        }
    }

    @Override
    public void unlock() {
        lock.set(false);
    }
}
