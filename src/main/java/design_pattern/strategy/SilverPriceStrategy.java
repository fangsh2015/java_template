package design_pattern.strategy;

/**
 * 银牌会员 89折
 * Created by Niki on 2018/4/2 8:55
 */
public class SilverPriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original * 0.89d;
    }
}
