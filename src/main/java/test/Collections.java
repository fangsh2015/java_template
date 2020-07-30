package test;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class Collections {
    public static void main(String[] args) {
        difference_apache();
    }

    public static void difference(){
        List<String> list1 = Lists.newArrayList("a","b","c","d","e","f","g");
        List<String> list2 = Lists.newArrayList("a","b","c");

        boolean list = list1.removeAll(list2);

        System.out.println(list1.toString());
        System.out.println(list2.toString());
    }

    public static void difference_apache(){
        List<String> list1 = Lists.newArrayList("a","b","c","d","e","f","g");
        List<String> list2 = Lists.newArrayList("a","b","c");

        List<String> list = (List<String>) CollectionUtils.subtract(list1, list2);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list);

    }
}
