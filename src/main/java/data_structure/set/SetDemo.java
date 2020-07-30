package data_structure.set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Niki on 2018/5/9 14:05
 */
public class SetDemo {
    public static void main(String[] args) {
        hashSetDemo();
        linkedHashSetDemo();

    }


    private static  void hashSetDemo() {
        Set<Integer> demo = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            demo.add(i);
        }
        System.out.println(demo);
    }

    private static void linkedHashSetDemo() {
        Set<Integer> demo = new LinkedHashSet<>();
        for (int i = 0; i < 10; i++) {
            demo.add(i);
        }
        System.out.println(demo);
    }

    private static void treeSetDemo() {
        Set<Integer> demo = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            demo.add(i);
        }
        System.out.println(demo);
    }
}
