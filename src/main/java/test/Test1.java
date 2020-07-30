package test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Niki on 2018/9/19 10:28
 */
public class Test1 {
    private void changeMap(Map<String, String> map) {
        Map<String, String> temp;
        temp = map;
        temp.put("test", "test");
        System.out.println("add test to map");
    }

    public static void main(String[] args) {
        Test1 test = new Test1();
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        System.out.println(map);
        test.changeMap(map);
        System.out.println(map);
    }
}
