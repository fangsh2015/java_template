package design_pattern.single;

/**
 * 懒汉模式
 * Created by Niki on 2018/5/30 11:12
 */
public class LazyLoadSingle {
    private LazyLoadSingle(){}

    private static LazyLoadSingle single = null;

    public static LazyLoadSingle getInstance() {
        if (single == null) {
            single = new LazyLoadSingle();
        }
        return single;
    }

    public static LazyLoadSingle safeGetInstance() {
        if (single == null) {
            synchronized (LazyLoadSingle.class) {
                if (single == null) {
                    single = new LazyLoadSingle();
                }
            }
        }
        return single;
    }

}
