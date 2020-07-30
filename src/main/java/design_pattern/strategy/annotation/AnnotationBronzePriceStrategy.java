package design_pattern.strategy.annotation;

import design_pattern.strategy.PriceStrategy;

/**
 * 铜牌会员 97折
 * Created by Niki on 2018/4/2 8:55
 */
@IntegralRegion(min = 500, max = 999)
public class AnnotationBronzePriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original;
    }
}
