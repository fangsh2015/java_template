package test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Created by Niki on 2020/12/15 9:29
 */
public class GCTest {

    public static void main(String[] args) {
        HashSetGCTest a = new HashSetGCTest("1", 1);
        HashSetGCTest b = new HashSetGCTest("1", 1);

        HashSet<HashSetGCTest> test = Sets.newHashSet(a, b);

        List<HashSetGCTest> test2 = Lists.newArrayList(a, b);
        System.out.println(test.contains(a));
        System.out.println(test2.contains(a));

        a.setA("11");

        System.out.println(test.contains(a));
        System.out.println(test2.contains(a));
    }

    @Data
    static class HashSetGCTest {
        private String a;
        private int b;

        public HashSetGCTest(String a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashSetGCTest that = (HashSetGCTest) o;
            return b == that.b &&
                    Objects.equals(a, that.a);
        }

        @Override
        public int hashCode() {

            return Objects.hash(a, b);
        }
    }

}
