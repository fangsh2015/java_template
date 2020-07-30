package j8.lambda.functional;

/**
 * Created by Niki on 2018/10/25 17:33
 */
@FunctionalInterface
public interface IFunctional<T> {
    T add(T t1, T t2);

    default String addHello(String hello) {
        return "你好" + hello;
    }
}
