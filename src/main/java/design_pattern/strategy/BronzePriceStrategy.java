package design_pattern.strategy;

/**
 * 铜牌会员 97折
 * Created by Niki on 2018/4/2 8:55
 */
public class BronzePriceStrategy implements PriceStrategy {
    @Override
    public double price(double original) {
        return original * 0.97d;
    }
}
