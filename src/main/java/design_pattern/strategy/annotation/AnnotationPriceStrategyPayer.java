package design_pattern.strategy.annotation;

import design_pattern.strategy.Customer;
import design_pattern.strategy.PriceStrategy;

import java.util.List;

/**
 * 基于注解的策略工厂
 * Created by Niki on 2018/4/2 9:29
 */
public class AnnotationPriceStrategyPayer {
    private Customer customer;
    private double price ;

    public AnnotationPriceStrategyPayer(Customer customer, double price) {
        this.customer = customer;
        this.price = price;
    }

    public double pay(){
        return AnnotationPriceStrategyFactory.getInstance().pay(this.customer, this.price);
    }
}
