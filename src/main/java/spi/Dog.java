package spi;

/**
 * Created by Niki on 2019/6/19 9:52
 */
public class Dog implements Shout {
    @Override
    public void shout() {
        System.out.println("汪汪汪！");
    }
}
