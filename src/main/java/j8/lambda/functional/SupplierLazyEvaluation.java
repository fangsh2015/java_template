package j8.lambda.functional;

import java.util.function.Supplier;

/**
 * 利用Supplier来进行延迟求值，以节约计算资源。
 * 某些不必要的计算，则不会进行计算
 * Created by Niki on 2018/10/29 16:30
 */
public class SupplierLazyEvaluation {

    public static boolean evaluate(final int value) {
        System.out.println("evaluating..." + value);
        //模拟计算过程
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value > 200;
    }

    public static void eagerEvaluator(final boolean input1, final boolean input2) {
        System.out.println("eagerEvaluator called...");
        System.out.println("accept ?" + (input1 && input2));
    }

    public static void evaluation(final int value, final int value2) {
        eagerEvaluator(evaluate(value), evaluate(value2));
    }


    /**
     * 利用supplier的get才真正执行的特点，避免了当第一个表达式为false时，第二个表达式执行的资源消耗
     *
     * @param s1
     * @param s2
     */
    public static void lazyEvaluator(final Supplier<Boolean> s1, final Supplier<Boolean> s2) {
        System.out.println("lazyEvaluator called...");
        System.out.println("accept ?" + (s1.get() && s2.get()));
    }

    public static void lazyEvaluation(final int value, final int value2) {
        lazyEvaluator(() -> evaluate(value), () -> evaluate(value2));
    }


}
