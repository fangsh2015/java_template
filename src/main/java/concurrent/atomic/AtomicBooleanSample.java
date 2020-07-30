package concurrent.atomic;

import sun.misc.Unsafe;

import java.io.Serializable;

/**
 * Created by Niki on 2018/8/27 9:27
 */
public class AtomicBooleanSample implements Serializable {
    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private static final long valueOffset;

    //使用volatile变量保存状态
    private volatile int value;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicBooleanSample.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public final boolean compareAndSet(boolean expect, boolean update) {
        int e = expect ? 1 : 0;
        int u = update ? 1 : 0;
        return unsafe.compareAndSwapInt(this, valueOffset, e, u);
    }

    public final boolean getAndSet(boolean newValue) {
        for (; ; ) {
            boolean current = get();
            if (compareAndSet(current, newValue)) {
                return current;
            }
        }
    }

    private boolean get() {
        return this.value == 1 ? true : false;
    }

    public final void lazySet(boolean newValue) {
        int v = newValue ? 1 : 0;
        unsafe.putOrderedInt(this, valueOffset, v);
    }
}
