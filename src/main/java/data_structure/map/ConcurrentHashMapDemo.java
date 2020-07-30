package data_structure.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Niki on 2018/2/9 9:05
 */
public class ConcurrentHashMapDemo {
    volatile String msg ;
    public static void main(String[] args) {
        testDeadCycle();
    }

    /**
     * ConcurrentHashMap 可能出现死循环的场景
     */
    public static void testDeadCycle(){
        Map<String, String> map = new ConcurrentHashMap<>();

        map.computeIfAbsent("a", key -> {
            map.put("a", "2");
            return "1";
        });
    }
}
