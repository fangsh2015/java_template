package j8.future;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Niki on 2018/5/9 15:40
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        testCompleteAction();
    }

    /**
     * 测试 join 和get 方法
     */
    private static void test() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
        System.out.format("join=%d", future.join());
        System.out.println("***************************");
        try {
            System.out.format("get=%d", future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在两个线程里并行执行任务A和任务B，只要有一个任务完成了，就执行任务C
     * 使用Future和CompletableFuture来实现
     */
    private static void useFuture() throws ExecutionException, InterruptedException {
        System.out.println("***useFuture***");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Void> futureA = executor.submit(() -> work("futureA"));
        Future<Void> futureB = executor.submit(() -> work("futureB"));
        while (true) {
            try {
//                futureA.get(1, TimeUnit.SECONDS);
//                break;
                if(futureA.isDone()){
                    futureA.get();
                    break;
                }
            }  catch (Exception e) {
                e.printStackTrace();
            }

            try {
//                futureB.get(1, TimeUnit.SECONDS);
//                break;
                if (futureB.isDone()) {
                    futureB.get();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.submit(() -> work("futureC"));

        executor.shutdown();
    }

    private static void useCompletableFuture() throws ExecutionException, InterruptedException {
        System.out.println("***useCompletableFuture***");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> futureA = CompletableFuture.runAsync(() -> work("futureA"));
        CompletableFuture<Void> futureB = CompletableFuture.runAsync(() -> work("futureB"));

        System.out.println("get = " + futureB.runAfterEither(futureA, () -> work("futureC")).get());

    }

    private static Random random = new Random();
    public static Void work(String name) {
        System.out.println(name + " starts at " + LocalTime.now());
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
        }
        System.out.println(name + " ends at " + LocalTime.now());
        return null;
    }


    private static void testCompleteAction() throws ExecutionException, InterruptedException {
        CompletableFuture<Map<String, String>> future = CompletableFuture.supplyAsync(CompletableFutureDemo::getMoreData);
        CompletableFuture futureRes = future.whenComplete((v,e)->{
            System.out.println("v=" + v);
            System.out.println("e=" + e);
        });
        System.out.println("get = "+futureRes.get());
    }
    private static void testCompleteAsyncAction() throws ExecutionException, InterruptedException {
        CompletableFuture<Map<String, String>> future = CompletableFuture.supplyAsync(CompletableFutureDemo::getMoreData);
        CompletableFuture futureRes = future.whenCompleteAsync((v,e)->{
            System.out.println("v=" + v);
            System.out.println("e=" + e);
        });
        System.out.println("get = "+futureRes.get());
    }

    static Map<String, String> getMoreData() {
        System.out.println("start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
        Map map = new HashMap();
        map.put(random.nextInt(1000), random.nextInt(1000));
        return map;
    }
}
