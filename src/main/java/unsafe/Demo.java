package unsafe;

import sun.reflect.Reflection;

/**
 * Created by Niki on 2018/9/11 15:24
 */
public class Demo {

    public void currentClass() {
        String className = null;
        String methodName = null;

        StackTraceElement[] elements = new Throwable().getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            if (this.getClass().getName().equals(elements[i].getClassName())) {
                /* 获取堆栈的下一个元素，就是调用者元素
                    如果想要获取当前方法所在的类的信息，直接读取elements[i]
                 */
                className = elements[i + 1].getClassName();
                methodName = elements[i + 1].getMethodName();
                break;
            }
        }
        System.out.println(String.format("当前调用者为%s,调用方法为：%s", className, methodName));
    }

    public void currentClass2() {
        Class clazz = Reflection.getCallerClass(2);
        System.out.println(String.format("当前调用者为：%s", clazz.getName()));
        clazz = Reflection.getCallerClass(1);
        System.out.println(String.format("当前方法所在类信息：%s",clazz.getName()));

    }

    static class Invoker {
        public static void call() {
            Demo demo = new Demo();
            demo.currentClass();
            demo.currentClass2();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.currentClass();
        demo.currentClass2();

        Invoker.call();

    }

    /**
     * 当前调用者为unsafe.Demo,调用方法为：main
     * 当前调用者为：unsafe.Demo
     * 当前方法所在类信息：unsafe.Demo
     * 当前调用者为unsafe.Demo$Invoker,调用方法为：call
     * 当前调用者为：unsafe.Demo$Invoker
     * 当前方法所在类信息：unsafe.Demo
     */
}
