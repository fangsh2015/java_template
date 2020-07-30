package jvm.instrumentation.method_time_monitor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * Agent类，用来获取Instrumentation对象
 * Created by Niki on 2018/4/18 14:31
 */
public class TimeMonitorAgent {
    private static Instrumentation instrumentation;
    private static Object lock = new Object();

    public TimeMonitorAgent() {

    }

    public static void premain(String args, Instrumentation instrumentation) {
        synchronized (lock) {
            if (instrumentation == null) {
                TimeMonitorAgent.instrumentation = instrumentation;
                System.out.println("init instrumentation");

            } else {
                System.out.println("instrumentation is inited ");
            }
        }

        ClassFileTransformer transformer = new TimeMonitorTransformet();

        /* 将transformer注册用来改变类的方法，增加监控时间的功能 */
        TimeMonitorAgent.instrumentation.addTransformer(transformer);
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
