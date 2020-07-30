package jvm.javaAgent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by Niki on 2018/4/10 16:54
 */
public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws ClassNotFoundException, UnmodifiableClassException,
            InterruptedException {
        inst.addTransformer(new Transformer(), true);
        inst.retransformClasses(TransClass.class);
        System.out.println("Agent Main Done");
    }
}
