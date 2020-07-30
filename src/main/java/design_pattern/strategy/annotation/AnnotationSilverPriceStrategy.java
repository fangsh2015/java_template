package design_pattern.strategy.annotation;

import design_pattern.strategy.PriceStrategy;

/**
 * 银牌会员 89折
 * Created by Niki on 2018/4/2 8:55
 */
@IntegralRegion(min = 1000, max = 2999)
public class AnnotationSilverPriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original * 0.89d;
    }
}
