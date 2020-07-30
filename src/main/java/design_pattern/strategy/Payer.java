package design_pattern.strategy;

/**
 * 支付类，用于计算一个客户应该支付的金额
 * 采用普通的策略模式
 * Created by Niki on 2018/4/2 9:01
 */
public class Payer {
    private PriceStrategy priceStrategy;
    private Customer customer;
    private double price ;

    public Payer(Customer customer, double price) {
        this.customer = customer;
        this.price = price;
    }

    /**
     * 客户最终支付金额
     * @return
     */
    public double pay() {
        int integral = customer.getIntegral();
        if (integral < 500) {
            priceStrategy = new OrdinaryPriceStrategy();
        } else if (integral < 1000) {
            priceStrategy = new BronzePriceStrategy();
        } else if (integral < 3000) {
            priceStrategy = new SilverPriceStrategy();
        } else {
            priceStrategy = new GoldPriceStrategy();
        }

        return priceStrategy.price(this.price);
    }

}
