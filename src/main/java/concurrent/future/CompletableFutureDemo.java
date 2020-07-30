package concurrent.future;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * CompletableFuture是对Future的增强。实现了Future和CompletionStage两个接口。其对Future的主要增强为：
 * 1、将两个异步计算合并为一个（这两个异步计算之间相互独立，同时第二个又依赖于第一个的结果）
 * 2、等待 Future 集合中的所有任务都完成。
 * 3、仅等待 Future 集合中最快结束的任务完成，并返回它的结果。
 * <p>
 * Created by Niki on 2018/12/24 9:03
 */
public class CompletableFutureDemo {
    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    public static void main(String[] args) throws IOException {
        thenApply();
        thenApply2();
        combin();
    }

    public static void thenApply() {
        long begin = System.currentTimeMillis();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> returnString("hello", 1000));

        CompletableFuture<String> future2 = future1.exceptionally((e) -> {
            e.printStackTrace();
            return "error";
        }).thenApplyAsync((v) -> v + returnString("world", 1500));

        String res = future2.join();

        System.out.println(String.format("结果为=%s,用时=%dms", res, System.currentTimeMillis() - begin));
    }

    private static void thenApply2() {
        long begin = System.currentTimeMillis();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> returnString("hello", 1000));
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> returnString("world", 1500));
        future1.thenAcceptBothAsync(future2, (s1, s2) -> System.out.println(String.format("结果为=%s,用时=%dms", s1 + s2, System.currentTimeMillis() - begin)));

    }

    private static void combin() {
        long begin = System.currentTimeMillis();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> returnString("hello", 1000));

        CompletableFuture<String> future2 = future1.thenCombine(CompletableFuture.supplyAsync(() -> returnString("world", 1500)), (s1, s2) -> returnString(s1 + " " + s2, 1));


        String res = future2.join();
        System.out.println(String.format("结果为=%s,用时=%dms", res, System.currentTimeMillis() - begin));

    }


    private static String returnString(String res, int waitMS) {
        try {
            Thread.sleep(waitMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void testListener() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("res");
        System.out.println(future.get());

        CompletableFuture future2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(100);
        });

        future2.get();

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
    }

    private static void test() {
        final CompletableFuture<Integer> future = compute();
        class Client extends Thread {
            CompletableFuture<Integer> f;

            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }

            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + " : " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        new Client("client1", future).start();
        new Client("client2", future).start();
        future.complete(200);
    }

/*
    在前一个阶段的基础上完成操作，并返回值
 */

    /**
     * 同步处理上一阶段的结果
     */
    @Test
    public void testThenApplyExample() {
        CompletableFuture future = CompletableFuture.completedFuture("message").thenApply((s) -> {
            sleep(2000);
            return s.toUpperCase();
        });
        // 非异步，知道s.toUpperCase()完成才返回值
        Assert.assertEquals("MESSAGE", future.getNow("null"));

    }

    /**
     * 异步处理上一阶段的结果
     */
    @Test
    public void testThenApplyAsyncExample() {
        CompletableFuture future = CompletableFuture.completedFuture("message").thenApplyAsync((s) -> {
            Assert.assertTrue(Thread.currentThread().isDaemon());
            sleep(2000);
            return s.toUpperCase();
        });
        // 异步，getNow立即返回给定的默认值
        Assert.assertNull(future.getNow(null));
        // 异步，join等待线程计算完成返回值
        Assert.assertEquals("MESSAGE", future.join());
    }
/*
   消费前一个阶段的值，并不返回结果
 */

    /**
     * 同步消费上一阶段的结果
     */
    @Test
    public void testThenAcceptExample() {
        StringBuilder result = new StringBuilder();
        String message = "thenAccept message";
        CompletableFuture.completedFuture(message)
                .thenAccept(s -> result.append(s));

        Assert.assertTrue("Result was not empty", result.length() == message.length());
    }

    /**
     * 异步消费上一步骤的结果
     */
    @Test
    public void testThenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        String message = "thenAccept message";
        CompletableFuture future = CompletableFuture.completedFuture(message).thenAcceptAsync(s -> {
            result.append(s);
            sleep(1000);
        });
        Assert.assertTrue("result was empty", result.length() == 0);
        future.join();
        Assert.assertTrue("result was :" + result.toString(), result.length() == message.length());
    }

    /**
     * CompletableFuture 异常处理过程
     * 1、join不再和get方法一样抛出受检查的异常，而是会抛出不受检查的异常CompletionException
     * 2、completableFuture可以通过handler方法来处理运行过程中的异常。异常参数将赋值给第二个参数，该方法在抛出异常后返回结果。
     * 3、
     */
    @Test
    public void testCompleteExceptionallyExample() {
        CompletableFuture future = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            sleep(1000);
            return s.toUpperCase();
        });
        // 异常处理Future
        CompletableFuture exceptionHandler = future.handle((v, e) -> {
            System.out.println("value:" + v);
            System.out.println("th:" + e.getClass() + ": " + e);
            return (e != null) ? "message upon cancel" : "";
        });
        // 任务future抛出异常
        future.completeExceptionally(new RuntimeException("completed exceptionally"));

        Assert.assertTrue("Was not completed exceptionally", future.isCompletedExceptionally());
        try {
            future.join();
        } catch (CompletionException ex) { // just for testing
            Assert.assertEquals("completed exceptionally", ex.getCause().getMessage());
        }
        Assert.assertEquals("message upon cancel", exceptionHandler.join());
    }


    /**
     * 两个任务，任意一个完成取其值进行处理
     */
    @Test
    public void testApplyToEitherExample() {
        String message = "Message";
        CompletableFuture future1 = CompletableFuture.completedFuture(message).thenApplyAsync(String::toUpperCase);
        CompletableFuture future2 = CompletableFuture.completedFuture(message).thenApplyAsync(String::toLowerCase);
        // future1和future2任意一个的结果加入到Function(s = s + "either apply")中处理并返回
        CompletableFuture<String> resFuture = future1.applyToEither(future2, s -> s + " either apply");
        System.out.println(resFuture.join());

        Assert.assertTrue(resFuture.join().endsWith("either apply"));
    }

    @Test
    public void testAcceptToEitherExample() {
        String message = "Message";
        CompletableFuture future1 = CompletableFuture.completedFuture(message).thenApplyAsync(String::toUpperCase);
        CompletableFuture future2 = CompletableFuture.completedFuture(message).thenApplyAsync(String::toLowerCase);

        CompletableFuture resFuture = future1.acceptEither(future2, System.out::println);
    }

    @Test
    public void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        System.out.println(result);
        System.out.println(original);
        Assert.assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void thenComposeExample() {
        String original = "Message";
        CompletableFuture<String> future1 = CompletableFuture.completedFuture(original).thenApplyAsync(String::toUpperCase);
        CompletableFuture<String> future2 = CompletableFuture.completedFuture(original).thenApplyAsync(String::toLowerCase);
        CompletableFuture<String> resFuture = future1.thenComposeAsync(upper -> future2.thenApplyAsync(s -> upper + s));
//        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
//                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(String::toLowerCase)
//                        .thenApply(s -> upper + s));

        System.out.println(resFuture.join());
        Assert.assertEquals("MESSAGEmessage", resFuture.join());
    }

    @Test
    public void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List messages = Arrays.asList("b", "c", "a");

        List<CompletableFuture> futures = (List<CompletableFuture>) messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture resFuture = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenCompleteAsync((res, th) -> {
            if (th == null) {
                System.out.println("sdf" + res);
                result.append(res);
            }
        });
        Assert.assertTrue("Result was empty", result.length() == 0);
        resFuture.join();
        Assert.assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = (List<CompletableFuture>) messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    System.out.println(v);
                    result.append("done");
                });
        allOf.join();
        Assert.assertTrue("Result was empty", result.length() > 0);
        System.out.println(result);
        for (CompletableFuture future : futures) {
            System.out.println(future.join());
        }
    }

    private String delayedUpperCase(Object s) {
        int randomSleep = new Random().nextInt(10) + 1;
        sleep(randomSleep * 100);
        return s.toString().toUpperCase();
    }

    private void sleep(int mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test2() {

        List<Integer> taskList = Arrays.asList(2, 1, 3, 5, 4, 6, 7, 9);
        List<String> res = new ArrayList<>();
        CompletableFuture[] cfs = taskList.stream().map(integer -> CompletableFuture.supplyAsync(() -> calc(integer)).thenApply(h -> Integer.toString(h)).whenComplete((s, e) -> {
            System.out.println(String.format("完成%s,y异常e=%s", s, e));
            res.add(s);
        })).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(cfs).join();

    }

    public static Integer calc(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(3000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }
}


