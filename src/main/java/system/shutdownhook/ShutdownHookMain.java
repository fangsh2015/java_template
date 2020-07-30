package system.shutdownhook;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 资源注册关闭钩子
 * Created by Niki on 2018/5/30 10:16
 */
public class ShutdownHookMain {
    private static ExecutorService executor = Executors.newFixedThreadPool(2);
    static {
        ShutdownHook.registerShutdownHook(new Closable() {
            @Override
            public void close() {
                System.out.println("在这里释放资源");
                if (!executor.isShutdown()) {
                    executor.shutdown();
                }
            }
        });
    }
    public static void main(String[] args) {
        //注册关闭钩子
        Runtime.getRuntime().addShutdownHook(ShutdownHook.getInstance());
    }
}
