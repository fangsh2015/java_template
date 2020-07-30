package concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * AQS实现二元闭锁
 * java并发编程14-14
 * Created by Niki on 2018/8/14 8:55
 */
public class OneShotLatch {
    private final Sync sync = new Sync();
    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        protected int tryAcquireShare(int ignored) {
            //如果闭锁是开的（state==1)操作成功，否则失败
            return (getState() == 1) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignore) {
            setState(1);
            return true;
        }
    }

}
