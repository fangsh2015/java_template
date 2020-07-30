package jvm.instrumentation.method_time_monitor;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 自定义ClassFileTransformer，用来修改字节码 增加方法调用时长监控
 * Created by Niki on 2018/4/18 9:14
 */
public class TimeMonitorTransformet  implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        ClassPool pool = null;
        CtClass ctClass = null;
        try {
            pool = ClassPool.getDefault();
            ctClass = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
            CodeConverter convert = new CodeConverter();

            if (ctClass.isInterface() == false) {
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (!methods[i].isEmpty()) {
                        AOPInsertMethod(methods[i]);
                    }
                }
                transformed = ctClass.toBytecode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } finally {
            if (ctClass != null) {
                ctClass.detach();
            }
        }

        return transformed;
    }

    /**
     * 为方法添加时间监控
     * @param method
     */
    private static void AOPInsertMethod(CtMethod method) throws CannotCompileException {
        method.instrument(new ExprEditor(){
            public void edit(MethodCall m) throws CannotCompileException {
                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
                        + m.getClassName() + "." + m.getMethodName()
                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
            }
        });
        //在方法体前后添加语句
//        method.insertBefore("System.out.println(\"enter method\"+System.currentTimeMillis());");
//        method.insertAfter("System.out.println(\"leave method\"+System.currentTimeMillis()););");
    }

}
