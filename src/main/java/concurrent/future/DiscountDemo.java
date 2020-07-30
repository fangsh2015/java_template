package concurrent.future;

import lombok.Getter;

import java.text.NumberFormat;

/**
 * 打折服务
 * Created by Niki on 2019/2/16 16:54
 */
public class DiscountDemo {
    @Getter
    public enum Discount {
        NONE(0), SILVER(5), GOLD(10), PLATNUM(15), DIAMOND(20);

        private final int percent;

        Discount(int percent) {
            this.percent = percent;
        }
    }

    public static void delay() {

        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String applyDiscount(String shop, Double price, Discount discount) {
        return shop + " price is " + apply(price, discount);
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShop() + " price is " + apply(quote.getPrice(), quote.getDiscount());
    }

    public static Double applyDiscount(Double price, Discount discount) {
        return price * (100 - discount.getPercent()) / 100;
    }


    private static String apply(double price, Discount discussion) {
        delay();
        return NumberFormat.getInstance().format(price * (100 - discussion.getPercent()) / 100);
    }

}
