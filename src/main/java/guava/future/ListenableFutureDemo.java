package guava.future;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Guava提供的ListenableFuture是对jdk的Future的增强
 * Created by Niki on 2019/2/18 11:03
 */
public class ListenableFutureDemo {
    static Random random = new Random();
    static ExecutorService service = Executors.newFixedThreadPool(2);
    static ListeningExecutorService listeningService = MoreExecutors.listeningDecorator(service);

    /**
     * 多个Future同时获取结果
     */
    public static void moreFutureGetResult(ExecutorService service) {
        ListeningExecutorService listeningService = MoreExecutors.listeningDecorator(service);
        List<ListenableFuture<Integer>> resList = Lists.newArrayListWithCapacity(10);
        for (int i = 0; i < 10; i++) {
            ListenableFuture<Integer> listenableFuture = listeningService.submit(() -> {
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return random.nextInt();
            });
            resList.add(listenableFuture);
        }

        ListenableFuture<List<Integer>> resFuture = Futures.allAsList(resList);

        try {
            resFuture.get().forEach(System.out::println);
//            Futures.transform(resFuture, new Function<List<Integer>, Object>() {
//                @Override
//                public Object apply(List<Integer> integers) {
//
//                }
//            });
            listeningService.shutdownNow();
//            res.forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void futureToListenableFuture() {
        Future<String> future = service.submit(() -> "1");
        ListenableFuture<String> test = JdkFutureAdapters.listenInPoolThread(future);
        test.addListener(() -> {
            System.out.println("任务完成");
        }, service);

        Futures.addCallback(test, new FutureCallback<String>() {
            @Override
            public void onSuccess(String s) {
                System.out.println(s + "任务成功");
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("任务失败" + throwable);
            }
        });
    }

    public static void shouldTestFuture() {
        ListenableFuture<Integer> future1 = listeningService.submit(() -> {
            Thread.sleep(1000);
            System.out.println("call future 1");
            return 1;
        });

        ListenableFuture<Integer> future2 = listeningService.submit(() -> {
            Thread.sleep(2000);
            System.out.println("call future 2");
            return 2;
        });

        ListenableFuture<List<Integer>> allFutures = Futures.allAsList(future1, future2);
    }

    public void listenableFutureTest() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(service);
        ListenableFuture future = listeningExecutorService.submit(() -> {
            System.out.println("异步任务开始执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务执行完成");

        });

        future.addListener(() -> {
            System.out.println("监听异步任务完成");
        }, service);

        ListenableFuture<Integer> futureCallBack = listeningExecutorService.submit(() -> {
            System.out.println("异步任务开始执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务执行完成");
            return 100;
        });

        Futures.addCallback(futureCallBack, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                System.out.println(String.format("异步任务执行完成，返回值为:%d", integer));
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(String.format("异步任务执行失败，异常信息：%s", throwable));
            }
        });
    }

    public static void futureTest() {
        ListenableFuture<String> future1 = listeningService.submit(() -> {
            System.out.println("future1任务");
            return "future1";
        });

        ListenableFuture<String> future2 = Futures.transform(future1, new Function<String, String>() {

            @Override
            public String apply(String s) {
                System.out.println("future2任务");
                return "future2";
            }
        }, listeningService);

        Futures.addCallback(future2, new FutureCallback<String>() {
            @Override
            public void onSuccess(String s) {
                System.out.println("链式异步任务成功");
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("链式异步任务失败");
            }
        }, listeningService);

    }

    public static void futureTest2() {
        ListenableFuture<Integer> future1 = listeningService.submit(() -> {
            Thread.sleep(1000);
            System.out.println("future1任务"+Thread.currentThread().getName());
            return 1;
        });

        ListenableFuture<String> future2 = Futures.transformAsync(future1, new AsyncFunction<Integer, String>() {
            @Override
            public ListenableFuture<String> apply(Integer s) throws Exception {
                Thread.sleep(1000);
                System.out.println("future2任务"+Thread.currentThread().getName());
                System.out.println("future1任务的结果="+s);
                return Futures.immediateFuture("future2任务");
            }
        }, listeningService);

        ListenableFuture<String> future3 = Futures.transformAsync(future2, new AsyncFunction<String, String>() {
            @Override
            public ListenableFuture<String> apply(String s) throws Exception {
                Thread.sleep(1000);
                System.out.println("future3任务"+Thread.currentThread()+Thread.currentThread().getName());
                return Futures.immediateFuture("future3任务");
            }
        },listeningService);


        
        
//        Futures.addCallback(future3, new FutureCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//                System.out.println("链式异步任务成功"+Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                System.out.println("链式异步任务失败"+Thread.currentThread().getName());
//            }
//        },listeningService);

        ListenableFuture<String> resFuture = Futures.whenAllComplete(future1, future2, future3).call(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return future1.get() + future2.get() + future3.get();
            }
        },listeningService);

        Futures.addCallback(resFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("所有的都完成了"+result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("失败");
            }
        },listeningService);
        
    }
    
    private static void futureTest3() throws ExecutionException, InterruptedException {
        ListenableFuture<String> future1 = listeningService.submit(() -> {
            Thread.sleep(1000);
            System.out.println("future1任务");
            return "future1";
        });

        ListenableFuture<String> future2 = listeningService.submit(() -> {
            Thread.sleep(1000);
            System.out.println("future2任务");
            return "future2";
        });

        ListenableFuture<String> lastFuture = Futures.whenAllComplete(future1, future2).call(() -> {
            Thread.sleep(1000);
            System.out.println("任务全部完成");
            return "finisy";
        }, listeningService);

        lastFuture.get();
    }
    
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        futureToListenableFuture();
//        moreFutureGetResult(service);
        long begin = System.currentTimeMillis();
        futureTest3();
        System.out.println("共使用时间=" + (System.currentTimeMillis() - begin));
    }
}
