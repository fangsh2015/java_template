package concurrent;

/**
 * Created by Niki on 2018/8/11 11:13
 */
public class Main {
    public static void main(String[] args) {
        ThreadGate gate = new ThreadGate();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                System.out.println("我是线程：" + Thread.currentThread().getName() + "在等待阀门开启");
                try {
                    gate.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是线程：" + Thread.currentThread().getName() + "，我已经通过阀门");
            });
            thread.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gate.open();

//        gate.close();
    }
}

