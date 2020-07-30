package design_pattern.strategy.annotation;

import design_pattern.strategy.PriceStrategy;

/**
 * 金牌会员 85折
 * Created by Niki on 2018/4/2 8:57
 */
@IntegralRegion(min = 3000)
public class AnnotationGoldPriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original * 0.85d;
    }
}
