package jvm.javaPremain;

import java.lang.instrument.Instrumentation;

/**
 * Created by Niki on 2018/4/10 17:01
 */
public class MyPremain {

    /**
     * 修改类
     * @param agentArgs class的路径
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("执行premain1" + agentArgs);
        inst.addTransformer(new Transformer());
    }

    public static void premain(String agentArgs){
        System.out.println("执行premain2" + agentArgs);
    }
}
