package guava.current_limiting;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Created by Niki on 2019/3/9 11:01
 */
public class RateLimiteDemo {

    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(1);
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
//        System.out.println(rateLimiter.tryAcquire(1));
//        System.out.println(rateLimiter.tryAcquire(1));
//        System.out.println(rateLimiter.tryAcquire(3));


    }
}
