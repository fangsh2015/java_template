package design_pattern.strategy.annotation.universal;

/**
 * 通用的策略工厂
 * 策略工厂， 通过入参得到一个出参
 * Created by Niki on 2020/12/17 11:12
 */
public interface UniversalStrategyFactory <P,R>  {

    /**
     * 策略工厂根据策略得到相应的结果
     *
     * @param v
     * @return
     */
    R apply(P v);




}
