package concurrent.future;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.FutureTask;

/**
 * Future实现多任务并发
 * ExecutorService + Callable = Future
 * Created by Niki on 2018/5/9 18:40
 */
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LocalTime.now();
        //开启多线程
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Integer> list = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(executor.submit(new CallableTask(i + 1)));
        }

        System.out.println("并行任务开始时间" + LocalTime.now());
        for (Future<Integer> future : futures) {
            while (true) {//轮询查看任务是否完成
                if (future.isDone() && !future.isCancelled()) {
                    int i = future.get();
                    System.out.println("任务i=" + i + "获取完成!" + LocalTime.now());
                    list.add(i);
                    break ;
                }
            }
        }
    }

    static class CallableTask implements Callable<Integer> {
        Integer i;

        public CallableTask(Integer i) {
            super();
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            if (i == 1) {
                Thread.sleep(3000);//任务1耗时3秒
            } else if (i == 5) {
                Thread.sleep(5000);//任务5耗时5秒
            } else {
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！");
            return i;
        }
    }

    public static void futureDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Future<String> future = executor.submit(() -> {
            return "这是一个Future的测试Demo";
        });

        if (future.isDone()) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public static void futureTaskDemo() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            return "这是一个FutureTask测试Demo";
        });

        if (futureTask.isDone()) {
            try {
                futureTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


}


