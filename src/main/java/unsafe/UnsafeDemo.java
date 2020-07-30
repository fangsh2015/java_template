package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * Created by Niki on 2018/8/22 9:14
 */
public class UnsafeDemo {

    private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        return unsafe;
    }

    private static void info(Unsafe unsafe) {
        unsafe.addressSize();
        unsafe.pageSize();
    }

    private static void objects(Unsafe unsafe, Object object) throws InstantiationException {
        Object obj = unsafe.allocateInstance(object.getClass());
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            long offset = unsafe.objectFieldOffset(field);
            System.out.println(String.format("字段%s的偏移量为：%d", field.getName(), offset));
        }
    }

    private static void arrays(Unsafe unsafe, Class clazz) {
        int offset = unsafe.arrayBaseOffset(clazz);
        int scale = unsafe.arrayIndexScale(clazz);
    }


    private static void synchronize(Unsafe unsafe, Object lock) {
        unsafe.tryMonitorEnter(lock);
        unsafe.monitorEnter(lock);
        unsafe.monitorEnter(lock);
    }

    private static void memory(Unsafe unsafe) {
        unsafe.allocateMemory(8);
        unsafe.copyMemory(1, 2, 3);
        unsafe.freeMemory(8);
    }

    private static void classes(Unsafe unsafe, Class clazz) throws NoSuchFieldException {
//        Field staticField = clazz.getField("staticField");
//
//        long offset = unsafe.staticFieldOffset(staticField);
//
//        unsafe.defineclas
    }


    static class Guard {
        private int ACCESS_ALLOWED = 1;

        public boolean giveAccess() {
            return 42 == ACCESS_ALLOWED;
        }
    }

    /**
     * 通过unsafe修改对象中变量的值
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static void changeValue() throws NoSuchFieldException, IllegalAccessException {
        Guard guard = new Guard();
        guard.giveAccess();   // false, no access

        //利用反射修改私有变量的值
        Field field = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
        field.setAccessible(true);
        field.setInt(guard, 42);
        System.out.println(guard.giveAccess());

        //利用unsafe修改对象私有变量的值，这种方式的另一个意义就是，无需对象引用，只需要知道对象在内存中的位置即可
        Unsafe unsafe = getUnsafe();
        Field f = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
        unsafe.putInt(guard, unsafe.objectFieldOffset(f), 42); // memory corruption

        System.out.println(guard.giveAccess()); // true, access granted
    }

    private static long sizeOfObject(Object object) throws NoSuchFieldException, IllegalAccessException {
        Unsafe u = getUnsafe();
        HashSet<Field> fields = new HashSet<>();
        Class c = object.getClass();
        while (c != Object.class) {
            for (Field field : c.getDeclaredFields()) {
                // 返回变量的修饰符
                // 取出对象的非静态字段
                if ((field.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(field);
                }
            }
            c = c.getSuperclass();
        }

        long maxSize = 0;
        for (Field field : fields) {
            long offset = u.objectFieldOffset(field);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }
        return ((maxSize / 8) + 1) * 8;
    }

    private static long sizeOf(Object object) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        long value = normalize(unsafe.getInt(object, 4L));
        return unsafe.getAddress(value + 12L);
    }

    private static long normalize(int value) {
        if (value >= 0) {
            return value;
        }
        return (~0L >>> 32) & value;
    }

    static class Test {
        private static int i = 1;
        private int ii;
        public int i3 = 3;

        public Test() {
            this.ii = 2;
        }

        public int getIi() {
            return ii;
        }

        public int getI() {
            return i;
        }
    }

    private static void testObjectInit() throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        Test test = new Test();
        System.out.println(test.getIi());
        System.out.println(test.getI());
        System.out.println(test.i3);

        Unsafe unsafe = getUnsafe();
        Test test1 = (Test) unsafe.allocateInstance(Test.class);
        System.out.println(test1.getIi());
        System.out.println(test.getI());
        System.out.println(test.i3);
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        String password = new String("l00k@myHor$e");
        String fake = new String(password.replaceAll(".", "?"));
        System.out.println(password); // l00k@myHor$e
        System.out.println(fake); // ????????????

        testObjectInit();
    }
}
