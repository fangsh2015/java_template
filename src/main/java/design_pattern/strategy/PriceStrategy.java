package design_pattern.strategy;

/**
 * 价格策略 根据会员积分的不同，有不同的价格折扣
 * Created by Niki on 2018/4/2 8:52
 */
public interface PriceStrategy {

    double price(double original);
}
