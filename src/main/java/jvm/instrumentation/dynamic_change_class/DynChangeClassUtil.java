package jvm.instrumentation.dynamic_change_class;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import sun.misc.Launcher;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 动态修改类的Util
 * Created by Niki on 2018/4/19 19:36
 */
public class DynChangeClassUtil {

    /**
     * 获取当前JVM的进程pid
     *
     * @return
     */
    private String getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println("当前JVM的PID is " + pid);
        return pid;
    }

    /**
     * 获取代理jar包的路径
     *
     * @return
     */
    private String getAgentJarPath() {
        URL url = DynChangeClassAgent.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String filePath = URLDecoder.decode(url.getPath(), "utf-8");
            //代理必须是jar包
            if (filePath.endsWith(".jar")) {
                File file = new File(filePath);
                if (file.exists()) {
                    return file.getAbsolutePath();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("代理" + DynChangeClassAgent.class.getName() + "不存在!");
    }

    /**
     * 当前JVM中加载代理Jar
     *
     * @param agentJarPath
     */
    private void loadAgent(String pid, String agentJarPath) {
        VirtualMachine virtualMachine = null;
        try {
            virtualMachine = VirtualMachine.attach(pid);
            virtualMachine.loadAgent(agentJarPath);
        } catch (AttachNotSupportedException | IOException e) {
            throw new RuntimeException("后去当前运行JVM异常！", e);
        } catch (AgentLoadException | AgentInitializationException e) {
            throw new RuntimeException("当前jvm加载路径为：" + agentJarPath + "的代理失败！", e);
        }


    }

    /* 代理对象中Instrumentation静态变量的变量名 */
    private static final String AGENT_INSTRUMENTATION_FIELD_NAME = "instrumentation";

    /**
     * 从代理对象处获取Instrumentation。
     * 注意，如果是把工程打成war包到Tomcat时，可能无法直接调用方法获得，必须从Java的默认加载器中通过反射的方式获取Instrumentation对象
     *
     * @return
     */
    private Instrumentation getInstrumentation() {
        Instrumentation instrumentation = DynChangeClassAgent.getInstrumentation();
        if (instrumentation == null) {
            //获取当前JVM的默认类加载器
            ClassLoader appClassLoader = Launcher.getLauncher().getClassLoader();
            try {
                Class dynChargeClassAgentClass = appClassLoader.loadClass(DynChangeClassAgent.class.getName());
                Field field = dynChargeClassAgentClass.getField(AGENT_INSTRUMENTATION_FIELD_NAME);
                field.setAccessible(true);
                instrumentation = (Instrumentation) field.get(dynChargeClassAgentClass);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (instrumentation == null) {
            throw new RuntimeException("获取Instrumentation失败， Instrumentation must not be null");
        }
        return instrumentation;
    }

    /**
     * 动态修改class对象
     * @param newClassMap
     *  key为类全名，value为新类的byte数组
     */
    public void defineClass(Map<String, byte[]> newClassMap) {
        if (newClassMap == null || newClassMap.isEmpty()) {
            return;
        }
        String pid = getPid();
        String agentJarPath = getAgentJarPath();
        loadAgent(pid, agentJarPath);
        Instrumentation instrumentation = getInstrumentation();

        List<ClassDefinition> classDefinitions = new ArrayList<>();
        for (String className : newClassMap.keySet()) {
            try {
                Class clazz = Class.forName(className);
                classDefinitions.add(new ClassDefinition(clazz, newClassMap.get(className)));
            } catch (ClassNotFoundException e) {
                System.out.println("当前jvm中不存在class：" + className);
                continue;
            }
        }
        ClassDefinition[] calssDefinitionArr = new ClassDefinition[classDefinitions.size()];
        calssDefinitionArr = classDefinitions.toArray(calssDefinitionArr);
        try {
            instrumentation.redefineClasses(calssDefinitionArr);
        } catch (ClassNotFoundException |UnmodifiableClassException e) {
            throw new RuntimeException("重定义Class异常！", e);
        }
    }
}
