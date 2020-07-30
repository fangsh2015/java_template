package j8.lambda.use;

import concurrent.counter.Counter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 求一断数据范围内的质数
 * Created by Niki on 2018/11/20 15:31
 */
public class PrimeNumber {

    /**
     * 判断自然数是否为质数
     * 质数：大于1， 并且除了1和本身外，无法被其他自然数整除
     *
     * @param number
     * @return
     */
    public static boolean isPrime(final int number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(divisor -> number % divisor == 0);
    }

    /**
     * 计算自number起下一个质数
     *
     * @param number
     * @return
     */
    private static int primeAfter(final int number) {
        if (isPrime(number + 1)) {
            return number + 1;
        } else {
            return primeAfter(number + 1);
        }

    }

    public static List<Integer> primes(final int fromNumber, final int count) {
        return Stream.iterate(primeAfter(fromNumber - 1), PrimeNumber::primeAfter).limit(count).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(primeAfter(3));

        System.out.println(Stream.iterate(0, t -> t + 1).limit(10).collect(Collectors.toList()));

        System.out.println(Stream.iterate(0, t->t+1).limit(20).findFirst().get());
    }
}
