package j8.lambda.functional;

import java.util.function.Supplier;

/**
 * 关于Supplier相关的Demo
 * Created by Niki on 2018/10/29 16:19
 */
public class SupplierLazyInit {

    /**
     * 利用Supplier实现一个多线程环境下的单利需求
     */
    static class Heavy {
        public Heavy() { System.out.println("Heavy created"); }

        @Override
        public String toString() {
            return "quite heavy";
        }
    }


    //  private Supplier<Heavy> heavy = Heavy::new;
    private Supplier<Heavy> heavy = () -> createAndCacheHeavy();

    private Heavy getHeavy() {
        return heavy.get();
    }

    private synchronized Heavy createAndCacheHeavy() {
        class HeavyFactory implements Supplier<Heavy> {
            private final Heavy heavyInstance = new Heavy();

            @Override
            public Heavy get() {
                return this.heavyInstance;
            }
        }

        if (!HeavyFactory.class.isInstance(heavy)) {
            heavy = new HeavyFactory();
        }
        return heavy.get();
    }

}
