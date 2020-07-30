package object.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * java的四大引用类型
 * Created by Niki on 2018/6/15 8:28
 */
@Slf4j
public class ReferenceDemo {

    public static void main(String[] args) {

        Object obj = new Object();
        Object count = new Object();

        //引用队列，当JVM进行GC回收对象是，GC将已注册的引用对象添加到该队列中
        ReferenceQueue referenceQueue = new ReferenceQueue();

        //弱引用
        SoftReference<Object> s = new SoftReference<>(obj, referenceQueue);
        //虚幻引用
        PhantomReference<Object> p = new PhantomReference<>(count, referenceQueue);

        obj = null;
        count = null;

        System.gc();

        try {
//            Reference<Object> ref = referenceQueue.remove(5000L);
            while (true) {
                Reference<Object> ref = referenceQueue.poll();
                System.out.println(String.format("引用对象是否为null：%s", ref == null));
                if (ref != null) {
                    System.out.println(ref.getClass());
                    if (ref instanceof SoftReference) {
                        System.out.println("软引用被回收，可在此进行处理！");
                    } else if (ref instanceof PhantomReference) {
                        System.out.println("虚幻引用被回收，可在此进行处理！");
                    }
                } else {
                    Thread.sleep(1000);
                    for (int i = 0; i < 100000; i++) {

                        Object temp = new Object();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
