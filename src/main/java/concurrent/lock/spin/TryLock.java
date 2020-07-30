package concurrent.lock.spin;

import java.util.concurrent.TimeUnit;

/**
 * Created by Niki on 2018/8/7 8:59
 */
public interface TryLock extends Lock{
    public boolean trylock(long time, TimeUnit unit);
}
