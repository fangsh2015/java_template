package j8.lambda.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Niki on 2018/10/26 9:29
 */
public class StreamDemo {
    public static void main(String[] args) {
        iterateDemo();
    }

    private static void toIntStream() {
        IntStream stream1 = IntStream.of(new int[]{1, 2, 3, 4});

        // 1 - 3
        IntStream stream2 = IntStream.range(1, 4);

        // 1 - 4
        IntStream stream3 = IntStream.rangeClosed(1, 4);

    }

    private static void demo2() {
        Stream<String> stream = Stream.of("hello", "world", "tom");

        String[] arr = stream.toArray(String[]::new);

        List<String> strList = stream.collect(Collectors.toList());
        List<String> strList2 = stream.collect(Collectors.toCollection(ArrayList::new));

        Set<String> strSet = stream.collect(Collectors.toSet());
        Set<String> strSet2 = stream.collect(Collectors.toCollection(HashSet::new));

        String str = stream.collect(Collectors.joining(","));
    }

    /**
     * peek方法和Map方法都会返回一个新的Stream对象，不同之处在于
     * peek不会对加入的对象进行处理。其方法接受的函数接口为consumer。没有返回类型。因此Stream对象的类型为入参的类型
     * map方法接受的函数接口为Function。所以map方法返回的Stream对象的类型为Function方法返回参数的类型。
     */

    private static void peekDemo() {
        List<String> list = Arrays.asList("one", "two", "three", "four");
        List<String> res = list.stream().filter(e -> e.length() > 3)
                .peek(s -> System.out.println("长度大于三的值：" + s))
                .filter(e -> e.length() > 4)
                .peek(s -> System.out.println("长度大于四的值：" + s))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(res);
    }

    private static void mapDemo() {
        List<String> list = Arrays.asList("one", "two", "three", "four");
        List<Integer> res = list.stream().filter(e -> e.length() > 3)
                .map(s -> {
                    System.out.println("长度大于三的值：" + s);
                    return s;
                })
                .filter(e -> e.length() > 4)
                .map(s -> {
                    System.out.println("长度大于四的值：" + s);
                    return s.length();
                })
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(res);
    }

    /**
     * 类似于一个递归的方法。每次的入参都是前一个计算的结果
     */
    private static void iterateDemo() {
        Stream.iterate(0, n -> n + 3)
                .limit(10)
                .forEach(x -> System.out.print(x + " "));
    }
}
