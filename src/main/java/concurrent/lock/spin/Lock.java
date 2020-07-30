package concurrent.lock.spin;

/**
 * 锁接口，实现两个基本方法，加锁和释放
 * Created by Niki on 2018/7/20 8:42
 */
public interface Lock {
    void lock();

    void unlock();
}
