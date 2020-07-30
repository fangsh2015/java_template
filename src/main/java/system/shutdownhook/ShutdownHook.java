package system.shutdownhook;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;

/**
 * motan关闭钩子实现方式
 * Created by Niki on 2018/5/30 10:00
 */
@Slf4j
public class ShutdownHook extends Thread {
    private ShutdownHook() {

    }

    //    private static ShutdownHook instance;
    private ArrayList<closableObject> resourceList = new ArrayList();
    private static final int defaultPriority = 20;

    @Override
    public void run() {
        closeAll();
    }

    private synchronized void closeAll() {
        Collections.sort(resourceList);
        for (closableObject resource : resourceList) {
            try {
                resource.closable.close();
            } catch (Exception e) {
                log.error("fail to close " + resource.closable.getClass(), e);
            }
            log.info("success to close " + resource.closable.getClass());
        }
        resourceList.clear();
    }

    public static void registerShutdownHook(Closable closable) {
        registerShutdownHook(closable, defaultPriority);
    }

    private static void registerShutdownHook(Closable closable, int defaultPriority) {

        getInstance().resourceList.add(new closableObject(closable, defaultPriority));
        log.info("add resource " + closable.getClass() + " to list");
    }

    private static class closableObject implements Comparable<closableObject> {

        Closable closable;
        int priority;

        public closableObject(Closable closable, int priority) {
            this.closable = closable;
            this.priority = priority;
        }

        @Override
        public int compareTo(closableObject o) {
            if (this.priority > o.priority) return -1;
            else if (this.priority == o.priority) return 0;
            else return 1;
        }
    }

    public static ShutdownHook getInstance() {
        return SingleHandler.instance;
    }

    private static class SingleHandler {
        private static ShutdownHook instance;

        static {
            instance = new ShutdownHook();
        }

    }
}
