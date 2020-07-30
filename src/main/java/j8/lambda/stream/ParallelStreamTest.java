package j8.lambda.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamTest {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 1, 4, 6, 12, 43);

        List<Integer> filterList = list.parallelStream().filter(i -> i > 10).collect(Collectors.toList());

        System.out.println(filterList.toString());

    }
}

