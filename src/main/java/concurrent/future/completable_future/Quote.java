package concurrent.future.completable_future;

import concurrent.future.DiscountDemo;
import lombok.Data;

/**
 * 商品报价信息
 * <p>
 * Created by Niki on 2019/6/13 18:15
 */
@Data
public class Quote {
    // 商品价格
    private Double price;
    // 商品费率
    private Double rate;
    // 商品折扣
    private concurrent.future.DiscountDemo.Discount discount;
    // 商店
    private String shop;
    // 商品名称
    private String product;

    public String parse() {
        return String.format("商品：%s在%s商店的价格为：%s", product, shop, getPrice());
    }

    public String getPrice(){
        return DiscountDemo.applyDiscount(shop, price * rate, discount);
    }
}
