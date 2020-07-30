package jvm.dynamic_load;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2018/4/10 15:13
 */
public class JavaAgent {
    private static String classesPath;
    private static String jarPath;
    private static VirtualMachine virtualMachine;
    private static String pid;

    static {
        classesPath = JavaAgent.class.getClassLoader().getResource("").getPath();
        System.out.println("classesPath is " + classesPath);
        jarPath = getJarPath();
        System.out.println("jarpath is" + jarPath);

        //当前进程pid
        String name = ManagementFactory.getRuntimeMXBean().getName();
        pid = name.split("@")[0];
        System.out.println("当前进程id为：" + pid);
    }

    /**
     * 获取jar包路径
     *
     * @return
     */
    public static String getJarPath() {
        URL url = StringUtils.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar")) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }

        File file = new File(filePath);
        filePath = file.getAbsolutePath();//获取window下正确路径
        return filePath;
    }

    private static void init()  {
//        virtualMachine.loadAgent(jarPath + "/javaagent.jar");
        try {
            virtualMachine = VirtualMachine.attach(pid);
            virtualMachine.loadAgent("E:\\fsh_workspace\\code\\javaagent\\javaagent.jar");
        } catch (AgentLoadException e) {
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } finally {
            try {
                virtualMachine.detach();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        Instrumentation instrumentation = JavaDynAgent.getInstrumentation();
//        Preconditions.checkNotNull(instrumentation, "initInstrumentation must not be null");
    }

    private static void destroy() throws IOException {
        if (virtualMachine != null) {
            virtualMachine.detach();
        }
    }

    /**
     * 重新加载类
     *
     * @param root
     * @param classArr
     */
    public static void javaAgent(String root, String[] classArr) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException, ClassNotFoundException, UnmodifiableClassException {
        init();
        try {
            //整理需要重定义的类
            List<ClassDefinition> classDefList = new ArrayList<>();
            for (String className : classArr) {
                Class<?> c = Class.forName(className);
                String classPath = (StringUtils.isNotBlank(root) ? root : classesPath) + className.replace(".", "/") + ".class";
                System.out.println("class redefined:" + classPath);

                byte[] bytesFromFile = getBytes(classPath);
                ClassDefinition classDefinition = new ClassDefinition(c, bytesFromFile);
                classDefList.add(classDefinition);

                // redefine
//                JavaDynAgent.getInstrumentation().redefineClasses(classDefList.toArray(new ClassDefinition[classDefList.size()]));
            }
        } finally {
            destroy();
        }
    }

    public static void main(String[] args) {
//        DynamicLoadTest1 test1 = new DynamicLoadTest1();
//
//        test1.test();
//        try {
//            javaAgent(null, new String[]{"jvm.dynamic_load.DynamicLoadTest1"});
//
//            DynamicLoadTest1 test2 = new DynamicLoadTest1();
//            test2.test();
//        } catch (AgentInitializationException e) {
//            e.printStackTrace();
//        } catch (AgentLoadException e) {
//            e.printStackTrace();
//        } catch (AttachNotSupportedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnmodifiableClassException e) {
//            e.printStackTrace();
//        }
    }

    private static byte[] getBytes(String filePath)  {
        File javaFile = new File(filePath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(javaFile);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            byte[] clazzInfo = outputStream.toByteArray();
            return clazzInfo;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null ;
    }

}
