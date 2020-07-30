package test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

public class TestSortSet {
    public static void main(String[] args) {
        test();
    }

    static void demo() {
        Map<String, String> data = Maps.newTreeMap();

        for(int i=0;i<10;i++){
            data.put("sm"+i,"sm"+i);
        }

        data.put("sm0_","sm0_");

        data.put("acd","acd");
        data.put("bef","bef");

        System.out.println(data.toString());

        Set<String> keys = data.keySet();

        List<String> keyList = Lists.newArrayList(keys);

        System.out.println(keyList.toString());


        java.util.Collections.sort(keyList, new StringComparator());

        System.out.println(keyList.toString());

    }

    static class StringComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            if(o1.contains("_") && !o2.contains("_")){
                return -1;
            }else if(!o1.contains("_") && o2.contains("_")){
                return 1 ;
            }
            return o1.compareTo(o2);
        }
    }

    private static void test() {
        Set<String> test = new HashSet();
        System.out.println(test.add("1"));
        System.out.println(test.add("1"));
        Map<String, String> map = new HashMap<String, String>();
        System.out.println(map.put("1", "1"));
        System.out.println(map.put("1", "2"));
        System.out.println(map.get("1"));
        System.out.println(map.get("1"));

        test.iterator();

        map.entrySet();


        Set<String> treeSet = new TreeSet<>();

    }
}
