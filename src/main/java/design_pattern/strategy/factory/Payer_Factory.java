package design_pattern.strategy.factory;

import design_pattern.strategy.Customer;
import design_pattern.strategy.PriceStrategy;

/**
 * 使用策略工厂的支付类
 * Created by Niki on 2018/4/2 9:08
 */
public class Payer_Factory {
    private PriceStrategy priceStrategy;
    private Customer customer;
    private double price ;

    public Payer_Factory(Customer customer, double price) {
        this.customer = customer;
        this.price = price;
    }

    /**
     * 这里可以看出，只是将支付逻辑放到了策略工厂里面，但是具体的判断逻辑还是存在。
     * 如果新增一个策略，则又需要新增一个if-else的判断。这也是简单工厂的缺点，对修改开放
     * @return
     */
    public double pay(){
        return PriceStrategyFactory.pay(this.customer, this.price) ;
    }
}
