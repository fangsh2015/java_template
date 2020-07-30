package data_structure.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Niki on 2018/4/6 15:57
 */
public class HashMapDemo {
    public static void main(String[] args) {
        testEntrySet();
    }

    /**
     * 测试onlyIfAbsent如果为true,则 插入相同的key，不修改之前的值
     */
    private void testOnlyIfAbsent() {
        Map<String, String> demo = new HashMap<>();
    }

    private static void testEntrySet() {
        HashMap<Integer, Integer> demo = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            demo.put(i , i );
        }
        System.out.println(demo.entrySet());
    }
}
