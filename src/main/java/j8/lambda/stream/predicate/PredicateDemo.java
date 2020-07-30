package j8.lambda.stream.predicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * predicate提供了函数编程的支持
 * 通过predicate来接受一段函数作为方法的参数
 * * Created by Niki on 2018/10/23 19:46
 */
public class PredicateDemo {
    public static void main(String[] args) {
        demo();
    }
    public static void demo() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("首字母以J开始的语言");
        filter(languages, s -> s.startsWith("J"));
        filter2(languages, s -> s.startsWith("J"));

        System.out.println("所有的语言");
        filter(languages, s -> true);

        System.out.println("不用任何一门语言");
        filter(languages, s -> false);
    }

    public static void filter(List<String> languages, Predicate<String> conditions) {
        languages.stream().filter(s -> conditions.test(s)).forEach(System.out::println);
    }

    /**
     * 两个条件同事满足
     * @param languages
     * @param conditions
     * @param conditions2
     */
    public static void filter_and(List<String> languages, Predicate<String> conditions, Predicate<String> conditions2) {
        Predicate<String> andConditions = conditions.and(conditions2);
        String res = languages.stream().filter(s -> andConditions.test(s)).collect(Collectors.joining(","));
        System.out.println(res);
    }

    /**
     * 任意满足一个条件即可
     * @param languages
     * @param conditions
     * @param conditions2
     */
    public static void filter_or(List<String> languages, Predicate<String> conditions, Predicate<String> conditions2) {
        Predicate<String> andConditions = conditions.or(conditions2);
        languages.stream().filter(s -> andConditions.test(s)).forEach(System.out::println);
    }

    public static void filter2(List<String> languages, FilterCondition<String> condition) {
        languages.stream().filter(s -> condition.filter(s)).forEach(System.out::println);
    }


    @FunctionalInterface
    interface FilterCondition<T> {
        boolean filter(T t);
    }


}
