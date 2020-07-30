package referection;

import sun.reflect.Reflection;

/**
 * 关于反射相关的Demo
 * Created by Niki on 2018/9/3 10:16
 */
public class ReferectionDemo {
    static class CalleeApp {

        public void call() {
//            Class<?> clazz = Reflection.getCallerClass();
            Class<?> clazz = Reflection.getCallerClass(0);
            System.out.println("Hello " + clazz);
            clazz = Reflection.getCallerClass(1);
            System.out.println("Hello " + clazz);
            clazz = Reflection.getCallerClass(2);
            System.out.println("Hello " + clazz);
            clazz = Reflection.getCallerClass(3);
            System.out.println("Hello " + clazz);
            clazz = Reflection.getCallerClass(4);
            System.out.println("Hello " + clazz);
        }
    }

    static class Caller1 {
        void run(CalleeApp calleeApp) {
            if (calleeApp == null) {
                throw new IllegalArgumentException("callee can not be null");
            }
            calleeApp.call();
        }
    }

    public static void main(String[] args) {
        CalleeApp app = new CalleeApp();
        Caller1 c1 = new Caller1();
        c1.run(app);
    }
}
