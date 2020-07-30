package concurrent;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Niki on 2018/5/9 18:56
 */
public class CompletionServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        int taskCount = 10;
        List<Integer> list = new ArrayList<Integer>();
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
        for (int i = 0; i < taskCount; i++) {
            Future<Integer> future = completionService.submit(new Task(i + 1));
            futureList.add(future);
        }

        //方法1：future是提交时返回的，遍历queue则按照任务提交顺序，获取结果
        for (Future<Integer> future : futureList) {
            Integer res = future.get();
            System.out.println("任务result=" + res + "获取到结果!" + LocalTime.now());
            list.add(res);
        }

        //方法2.使用内部阻塞队列的take()
//        for (int i = 0; i < taskCount; i++) {
//            Integer res = completionService.take().get();
//            System.out.println("任务result=" + res + "获取到结果!" + LocalTime.now());
//            list.add(res);
//        }

        executor.shutdown();
    }

    static class Task implements Callable<Integer> {
        Integer i;

        public Task(Integer i) {
            super();
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            if (i == 5) {
                Thread.sleep(5000);
            } else {
                Thread.sleep(1000);
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "任务i=" + i + ",执行完成！");
            return i;
        }

    }
}
