package concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Niki on 2018/12/18 19:55
 */
public class FutureTaskDemo {
    private static void test() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(90000);
                return "Hello World";
            }
        };
        FutureTask task = new FutureTask(callable);
        new Thread(task).start();

        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < 3; i++) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String helloWorld = null;
//                    try {
//                        helloWorld = (String) task.get();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + "********************************");
//                    System.out.println(helloWorld);
//                    System.out.println("********************************");
//                }
//            });
//            thread.start();
//        }
    }

    public static void main(String[] args) {
        test();
    }
}
