package jvm.instrumentation.dynamic_change_class;

import java.lang.instrument.Instrumentation;

/**
 * 该类需要单独打包成jar包使用
 * Created by Niki on 2018/4/19 19:35
 */
public class DynChangeClassAgent {
    private static Instrumentation instrumentation;
    private static Object lockObject = new Object();

    public DynChangeClassAgent() {
    }

    public static void agentmain(String args, Instrumentation ins) {
        synchronized (lockObject) {
            if (DynChangeClassAgent.instrumentation == null) {
                DynChangeClassAgent.instrumentation = ins;
                System.out.println(">>>>java agent 代理：init instrumentation");
                System.out.println(instrumentation == null ? ">>>>null" : ">>>>" + instrumentation.toString());
            } else {
                System.out.println(">>>>instrumentation is inited");
            }
        }
    }

    public static Instrumentation getInstrumentation() {
        return DynChangeClassAgent.instrumentation;
    }
}
