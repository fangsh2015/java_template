package j8.lambda.use;

import java.math.BigInteger;
import java.util.stream.Stream;

/**
 * 利用Lambda优化递归
 * 递归在处理大规模计算是容易造成StackOverflowError.可以通过【尾调用】的技术来避免。而Java并不直接支持尾调用，可以通过Lambda来实现
 * Created by Niki on 2018/11/8 18:01
 */
public class RecursionUse {
    public static int factorialRec(final int number) {
        if (number == 1) {
            return number;
        } else {
            return number * factorialRec(number - 1);
        }
    }

    @FunctionalInterface
    public interface TailCall<T> {
        TailCall<T> apply();

        default boolean isComplete() {
            return false;
        }

        default T result() {
            throw new Error("not implemented");
        }

        default T invoke() {
            return Stream.iterate(this, TailCall::apply)
                    .filter(TailCall::isComplete)
                    .findFirst()
                    .get()
                    .result();
        }
    }

    static class TailCalls {
        public static <T> TailCall<T> call(final TailCall<T> nextCall) {
            return nextCall;
        }

        public static <T> TailCall<T> done(final T value) {
            return new TailCall<T>() {
                @Override
                public boolean isComplete() {
                    return true;
                }

                @Override
                public T result() {
                    return value;
                }

                @Override
                public TailCall<T> apply() {
                    throw new Error("end of recursion");
                }
            };
        }
    }

    public static TailCall<Integer> factorialTailRec(final int facotrial, final int number) {
        if (number == 1) {
            return TailCalls.done(facotrial);
        } else {
            return TailCalls.call(() -> factorialTailRec(facotrial * number, number - 1));
        }
    }

    public static TailCall<BigInteger> factorialTailRec_BigInt(final BigInteger factorial, final BigInteger number) {
        if (number.equals(BigInteger.ONE)) {
            return TailCalls.done(factorial);
        } else {
            return TailCalls.call(() -> factorialTailRec_BigInt(multiply(factorial, number), decrement(number)));
        }
    }

    private static BigInteger multiply(final BigInteger first, final BigInteger second) {
        return first.multiply(second);
    }

    private static BigInteger decrement(final BigInteger number) {
        return number.subtract(BigInteger.ONE);
    }

    public static int factorialRec_(final int number) {
        return factorialTailRec(1, number).invoke();
    }

    public static void main(String[] args) {
//        System.out.println(factorialRec(5));
        System.out.println(factorialTailRec(1, 5).invoke());

//        System.out.println(factorialTailRec_BigInt(BigInteger.ONE, new BigInteger("5")).invoke());
    }
}
