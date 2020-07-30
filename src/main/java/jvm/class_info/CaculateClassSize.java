package jvm.class_info;

import com.totoro.agent.JavaDynAgent;
import sun.misc.Unsafe;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;

/**
 * 计算java对象大小
 * java对象主要有三部分组成：对象头，实例数据 ，填充（对象大小为8的倍数）
 * Created by Niki on 2018/6/23 11:27
 */
public class CaculateClassSize {
    public static void main(String[] args) {
//        getAllFieldOffset();
        getOop();
    }

    public static void caculateClassSizeByAgent() {
        ObjectA objectA = new ObjectA();
        Instrumentation instrumentation = JavaDynAgent.getInstrumentation();
        long size = instrumentation.getObjectSize(objectA);
        System.out.println("ObjectA对象实例大小为：" + size);
    }

    public static void caculatieClassSizeByUnsafe() throws IllegalAccessException {
        ClassIntrospector introspector = new ClassIntrospector();
        ClassIntrospector.ObjectInfo res ;
        res = introspector.introspect(new ObjectA());
        System.out.println(res.getDeepSize());

    }

    /**
     * 获取对象中类型指针的长度。默认8位，如果是32位操作系统，则会经过压缩为4位
     * @return
     */
    public static long getOop() {
        Unsafe unsafe = getUnsafe();
        long ObjectRefSize = unsafe.arrayIndexScale(Object[].class);
        System.out.println(String.format("对象中类型指针的长度为：%d", ObjectRefSize));
        return ObjectRefSize;
    }

    /**
     * 对象实例数据排列为：按照数据长短，由常到短排序，对象引用在最后。double/long -> float/int -> chart/short -> byte/boolean -> reference
     *
     */
    public static void getAllFieldOffset() {
        Unsafe unsafe = getUnsafe();
        Field[] fields = ObjectA.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(String.format("%s offset is %s", field.getName(), unsafe.objectFieldOffset(field)));
        }
    }

    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException  e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用来验证对象大小demo类
     * Created by Niki on 2018/6/23 11:31
     */
    private static class ObjectA {
        String string;
        int i1; // 4
        byte b1; // 1
        byte b2; // 1
        int i2;  // 4
        ObjectB obj; //4
        byte b3;  // 1
    }

    private static class ObjectB {

    }
}
