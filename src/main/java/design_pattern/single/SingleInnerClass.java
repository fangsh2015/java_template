package design_pattern.single;

/**
 * 静态内部类方式实现静态内部类
 * Created by Niki on 2018/5/30 10:47
 */
public class SingleInnerClass {
    private SingleInnerClass() {}

    static {
        System.out.println("外部类加载");
    }

    public static SingleInnerClass getInstance() {
        return SingleHandler.single;
    }

    private static class SingleHandler {
        private static SingleInnerClass single = new SingleInnerClass();
        static {
            System.out.println("静态内部类加载");
        }
    }

    public static void main(String[] args) {
        SingleInnerClass singleInnerClass ;
        System.out.println("分隔线小尾巴");
        singleInnerClass = SingleInnerClass.getInstance();
    }
}
