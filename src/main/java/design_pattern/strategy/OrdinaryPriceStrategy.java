package design_pattern.strategy;

/**
 * 普通会员 不打折
 * Created by Niki on 2018/4/2 8:54
 */
public class OrdinaryPriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original;
    }
}
