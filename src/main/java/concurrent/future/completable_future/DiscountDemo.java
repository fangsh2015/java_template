package concurrent.future.completable_future;

import java.text.NumberFormat;

/**
 * 折扣服务
 * Created by Niki on 2019/6/13 18:03
 */
public class DiscountDemo {
    public enum Discount {
        NONE(0),SILVER(5),GOLD(10),PLATNUM(15),DIAMOND(20);
        private final int percent;
        Discount(int percent){
            this.percent = percent;
        }
        public int getPercent() {
            return percent;
        }
    }
    public static void delay() {

        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static String apply(double price,Discount discussion) {
        delay();
        return NumberFormat.getInstance().format(price * (100 - discussion.getPercent())/100);
    }

}
