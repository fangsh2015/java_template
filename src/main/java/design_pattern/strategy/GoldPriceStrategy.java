package design_pattern.strategy;

/**
 * 金牌会员 85折
 * Created by Niki on 2018/4/2 8:57
 */
public class GoldPriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original * 0.85d;
    }
}
