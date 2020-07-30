package data_structure.map;

import java.util.TreeMap;

/**
 * Created by Niki on 2018/5/16 14:55
 */
public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> demo = new TreeMap();
        demo.put(1, 1);
        demo.put(2, 2);
        demo.put(4, 4);
        demo.put(3, 3);
        System.out.println(demo);
    }
}
