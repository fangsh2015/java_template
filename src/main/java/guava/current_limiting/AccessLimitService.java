package guava.current_limiting;

import com.google.common.util.concurrent.RateLimiter;
import com.sun.corba.se.pept.transport.ReaderThread;

/**
 * Created by Niki on 2019/3/9 11:21
 */
public class AccessLimitService {
    RateLimiter rateLimiter = RateLimiter.create(2);

    /**
     * 立即获得令牌，如果没有令牌则返回失败
     * @return true：获得令牌成功； false：获得令牌失败
     */
    public boolean tryAcquire() {
        boolean res = rateLimiter.tryAcquire();
        System.out.println(res);
        return res;
    }

    /**
     * 阻塞获得令牌，返回参数为等待时间
     * @return
     */
    public double acquire() {
        double res = rateLimiter.acquire();
        System.out.println(res);
        return res;
    }

}

