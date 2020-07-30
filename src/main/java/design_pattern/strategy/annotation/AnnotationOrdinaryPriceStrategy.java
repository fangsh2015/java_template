package design_pattern.strategy.annotation;

import design_pattern.strategy.PriceStrategy;

/**
 * 普通会员 不打折
 * Created by Niki on 2018/4/2 8:54
 */
@IntegralRegion(max = 499)
public class AnnotationOrdinaryPriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original;
    }
}
