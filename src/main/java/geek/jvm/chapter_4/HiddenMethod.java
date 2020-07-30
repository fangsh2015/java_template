package geek.jvm.chapter_4;

/**
 * Created by Niki on 2020/7/16 17:24
 */
public class HiddenMethod {
    static class Father{
        public static String getName() {
            return "Father";
        }
    }

    static class Son extends Father {
        public static String getName() {
            return "Son";
        }
    }

    public static void main(String[] args) {
        System.out.println(Father.getName());
        System.out.println(Son.getName());
        Father father = new Father();
        Father fson = new Son();
        Son son = new Son();
        System.out.println(father.getName());
        System.out.println(fson.getName());
        System.out.println(son.getName());

    }

}
