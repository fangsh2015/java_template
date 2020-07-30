package concurrent.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by Niki on 2018/6/23 10:50
 */
public class AtomicReferenceFieldUpdaterDemo {
    private Integer index ;

    private void demo() {
        AtomicReferenceFieldUpdater referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(AtomicReferenceFieldUpdaterDemo.class, Integer.class, "index");

        referenceFieldUpdater.getAndSet(this, "2");

    }


}
