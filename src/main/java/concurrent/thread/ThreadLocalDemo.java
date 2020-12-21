package concurrent.thread;

/**
 * Created by Niki on 2020/11/20 20:08
 */
public class ThreadLocalDemo {

    private static ThreadLocal<String> demoThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int tmp = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    demoThreadLocal.set("test" + tmp);
                }
            };

            Thread t = new Thread(runnable);
            t.start();
        }
    }
}
