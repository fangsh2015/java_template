package test;

/**
 * Created by Niki on 2018/7/18 8:42
 */
public class Person {
    private String name;
    private int age;
    private boolean sex;

    public void sayHi(){
        System.out.println("Say hi from ITer_ZC");
    }

    public static void main(String[] args){
        Person p = new Person();
        p.sayHi();

        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
