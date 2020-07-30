package test;

/**
 * Created by Niki on 2018/11/9 9:10
 */
public class Test3 {
    private int age;

    public Test3(int age) {
        this.age = age;

    }

    public void grow(int age_) {
        age += age_;
    }

    public void change(int age) {
        age = 40;
    }

    public static void main(String[] args) {
        Test3 test = new Test3(28);
        System.out.println(test.age);
        test.grow(3);
        System.out.println(test.age);
        test.change(test.age);
        System.out.println(test.age);
    }

}
