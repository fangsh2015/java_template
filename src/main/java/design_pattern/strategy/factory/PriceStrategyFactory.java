package design_pattern.strategy.factory;

import design_pattern.strategy.*;

/**
 * 策略模式工厂
 * Created by Niki on 2018/4/2 9:09
 */
public class PriceStrategyFactory {
    private PriceStrategyFactory(){}

    public static double pay(Customer customer, double price){
        int integral = customer.getIntegral();
        if (integral < 500) {
            return new OrdinaryPriceStrategy().price(price);
        } else if (integral < 1000) {
            return new BronzePriceStrategy().price(price);
        } else if (integral < 3000) {
            return new SilverPriceStrategy().price(price);
        } else {
            return new GoldPriceStrategy().price(price);
        }
    }

}
