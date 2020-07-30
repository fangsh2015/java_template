package guava.current_limiting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Niki on 2019/3/9 11:22
 */
public class AccessLimitTest {
    private AccessLimitService accessLimitService = new AccessLimitService();
    ExecutorService service = Executors.newFixedThreadPool(10);

    public void access() {
        try {
            Thread.sleep(1000);
            System.out.println("执行任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            accessLimitService.acquire();
            System.out.println(String.format("线程:%s请求方法拿到令牌", Thread.currentThread().getName()));
            service.submit(() -> {
                access();
            });

        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }


    public static void main(String[] args) throws InterruptedException {
        new AccessLimitTest().test();
    }
}
