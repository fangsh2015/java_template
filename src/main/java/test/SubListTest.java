package test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2018/5/3 17:38
 */
public class SubListTest {
    public static void main(String[] args) {
        List<Integer> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test.add(i);
        }

        List<Integer> subList = test.subList(1, 4);
        System.out.println(subList);
        test.set(1, 10);
        System.out.println(subList);

        test.add(100);
        /*
        java.util.ConcurrentModificationException
        重新修改了原List,中数据的个数，则操作subList时抛异常ConcurrentModificationException
         */
        System.out.println(subList);
    }
}
