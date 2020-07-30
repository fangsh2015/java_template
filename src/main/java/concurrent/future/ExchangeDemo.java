package concurrent.future;

/**
 * 费率服务
 * Created by Niki on 2019/2/16 17:09
 */
public class ExchangeDemo {
    public static double getRate(String source,String target){
        delay();
        return 10;
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
