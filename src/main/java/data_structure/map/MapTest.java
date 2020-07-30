package data_structure.map;

import com.google.common.collect.Maps;

import java.util.*;

public class MapTest {
    public static void main(String[] args) {
        Map<String, String> test = Maps.newHashMap();

        Map<String, String> map = new HashMap<>();
        map.put("1","1");

        Collections.synchronizedMap(test);

        Hashtable hashtable = new Hashtable();
        hashtable.put(1,1);

        TreeMap treeMap = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });

        System.out.println(hashtable.get(1));
    }
}
