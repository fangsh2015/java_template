package concurrent.future;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 计算商品价格对象
 * Created by Niki on 2019/2/16 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    // 商品价格
    private Double price;
    // 商品费率
    private Double rate;
    // 商品折扣
    private DiscountDemo.Discount discount;
    // 商店
    private String shop;
    // 商品名称
    private String product;

    public String parse() {
        return String.format("商品：%s在%s商店的价格为：%s", product, shop, getPrice());
    }

    public String getPriceStr(){
        return DiscountDemo.applyDiscount(shop, price * rate, discount);
    }
}
