package jvm.dynamic_load;

import java.lang.instrument.Instrumentation;

/**
 * Created by Niki on 2018/4/10 15:28
 */
public class JavaDynAgent2 {
    private static Instrumentation instrumentation;
    private static Object lockObject = new Object();

    public JavaDynAgent2() {
    }

    public static void agentmain(String args, Instrumentation inst) {
        Object var2 = lockObject;
        synchronized(lockObject) {
            if(instrumentation == null) {
                instrumentation = inst;
                System.out.println("0->" + inst);
            } else {
                System.out.println("1->" + inst);
            }

        }
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
