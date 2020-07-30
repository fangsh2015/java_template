package concurrent.thread;

/**
 * Created by Niki on 2019/3/19 8:54
 */
public class InheritableThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocal tq = new InheritableThreadLocal();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tq.set("父线程私有变量");
                System.out.println("父线程=" + Thread.currentThread().getName() + ":" + tq.get());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tq.set("子线程变量");
                        System.out.println("子线程"+Thread.currentThread().getName()+":"+tq.get());
                    }
                }).start();
            }
        }).start();

    }
}
