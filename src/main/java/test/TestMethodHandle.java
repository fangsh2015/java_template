package test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestMethodHandle {
    private String address;

    private String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private static long times = 10 * 10000 * 10000l;

    public static void testSet() {
        TestMethodHandle test = new TestMethodHandle();
        long now = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            test.setAddress("string");
        }
        System.out.println("testSet used time is: " + (System.currentTimeMillis() - now));
    }

    public static void testReflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TestMethodHandle test = new TestMethodHandle();

        Method method = test.getClass().getMethod("setAddress", String.class);
        long now = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            method.invoke(test, "string");
        }
        System.out.println("testReflect used time is: " + (System.currentTimeMillis() - now));
    }

    public static void testMethodHandle() throws Throwable {
        TestMethodHandle test = new TestMethodHandle();
        MethodHandle handle = MethodHandles.lookup().findVirtual(TestMethodHandle.class, "setAddress", MethodType.methodType(void.class, String.class));

        long now = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            handle.invoke(test, "string");
        }
        System.out.println("testMethodHandle used time is: " + (System.currentTimeMillis() - now));
    }

    public static void test() {
        try {
            MethodHandle max = MethodHandles.lookup().findStatic(Math.class, "max", MethodType.methodType(int.class, int.class, int.class));
            int max_num = (int) max.invokeExact(3, 2);
            System.out.println(max_num);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            MethodHandle min = MethodHandles.lookup().findStatic(Math.class, "min", MethodType.methodType(int.class, int.class, int.class));
            int min_num = (int) min.invokeExact(3, 2);
            System.out.println(min_num);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void testVirtual() {
        String test = "Hello World";
        try {
            MethodHandle startwithd = MethodHandles.lookup().findVirtual(String.class, "startsWith", MethodType.methodType(boolean.class, String.class));
            System.out.println((boolean) startwithd.invokeExact(test, "H"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            MethodHandle endswith = MethodHandles.lookup().findVirtual(String.class, "endsWith", MethodType.methodType(boolean.class, String.class));
            boolean end = (boolean) endswith.invokeExact(test, "fsh");
            System.out.println(end);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void testArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        MethodHandle get = MethodHandles.arrayElementGetter(int[].class);
        try {
            System.out.println(array.length);
            int value = (int) get.invoke(array, 3);
            System.out.println(value);
            System.out.println(array.length);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        MethodHandle set = MethodHandles.arrayElementSetter(int[].class);
        try {
            set.invoke(array, 2, 10);
//            Arrays.stream(array).forEach(System.out::print);
            Arrays.stream(array).forEach(i -> System.out.print(i + ","));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void test1() throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle mhOld = lookup.findVirtual(String.class, "substring", type);

        MethodHandle mhNew = MethodHandles.dropArguments(mhOld, 0, float.class, String.class);
    }

    public static void testInsertArguments() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(String.class, String.class);
        MethodHandle methodHandle = lookup.findVirtual(String.class, "concat", type);

        String value = (String) methodHandle.invoke("hello", "world");
        System.out.println(value);

        methodHandle = MethodHandles.insertArguments(methodHandle, 1, "---");

        value = (String) methodHandle.invoke("hello");
        System.out.println(value);
    }

    public static void testInsertArgument2() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(String.class, String.class);
        MethodHandle mh = lookup.findVirtual(String.class, "concat", type);

        MethodHandle mh1 = MethodHandles.insertArguments(mh, 0, "$");

        String value = (String) mh1.invoke("123");
        System.out.println(value);
    }

    public static void testFilterArgument() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle max = lookup.findStatic(Math.class, "max", type);

        MethodHandle str_leng = MethodHandles.lookup().findVirtual(String.class, "length", MethodType.methodType(int.class));

        int length = (int) str_leng.invoke("fangshuhao");
        System.out.println(length);

        MethodHandle max_str_length = MethodHandles.filterArguments(max, 0, str_leng, str_leng);

        length = (int) max_str_length.invoke("fsh", "fangshuhao");
        System.out.println(length);
    }


    public static boolean guardTest() {
        return Math.random() > 0.5;
    }

    public static void testGuardWithTest() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mtTest = lookup.findStatic(TestMethodHandle.class, "guardTest", MethodType.methodType(boolean.class));

        MethodHandle max = lookup.findStatic(Math.class, "max", MethodType.methodType(int.class, int.class, int.class));

        MethodHandle min = lookup.findStatic(Math.class, "min", MethodType.methodType(int.class, int.class, int.class));

        MethodHandle mh = MethodHandles.guardWithTest(mtTest, max, min);

        int value = (int) mh.invoke(3, 4);
        System.out.println(value);
    }

    public void doSomething(){
        System.out.println("在run方法中运行的方法！");
    }

    public void testRUN() throws NoSuchMethodException, IllegalAccessException {
        MethodHandle mh = MethodHandles.lookup().findVirtual(TestMethodHandle.class, "doSomething", MethodType.methodType(void.class));
        mh = mh.bindTo(this);

        Runnable runnable = MethodHandleProxies.asInterfaceInstance(Runnable.class, mh);
        new Thread(runnable).start();

    }

    public static void main(String[] args) throws Throwable {
        new TestMethodHandle().testRUN();

    }
}


