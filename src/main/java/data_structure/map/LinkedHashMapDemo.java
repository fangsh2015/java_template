package data_structure.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Niki on 2018/4/6 15:54
 */
public class LinkedHashMapDemo {
    public static void main(String[] args) {
        LinkedHashMap<String, String> demo = new LinkedHashMap<>();
        demo.put("1", "1");
        demo.put("2", "2");
        demo.put("3", "3");

        for (Map.Entry<String, String> entry : demo.entrySet())
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
    }
}
