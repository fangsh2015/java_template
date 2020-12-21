package design_pattern.strategy.annotation.universal;

/**
 * Created by Niki on 2020/12/17 11:18
 */
public interface Stragety<P, R> {

    /**
     * 策略接口根据策略得到相应的结果
     *
     * @param v
     * @return
     */
    R apply(P v);
}
