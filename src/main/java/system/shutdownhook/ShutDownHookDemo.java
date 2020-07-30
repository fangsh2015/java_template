package system.shutdownhook;

/**
 * Created by Niki on 2018/5/30 9:56
 */
public class ShutDownHookDemo {
    public void addShutdownHook(Thread hook) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("shutdownHooks"));
        }

    }
}
