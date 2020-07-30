package design_pattern.strategy;

/**
 * 顾客类，用于保存顾客的信息
 * Created by Niki on 2018/4/2 8:59
 */
public class Customer {
    /*
    顾客积分，最小单位为1积分
    0-500 ordinary
    500-1000 bronze
    1000-3000 silver
    3000-~ gold
     */
    private int integral;

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getIntegral() {
        return integral;
    }
}
