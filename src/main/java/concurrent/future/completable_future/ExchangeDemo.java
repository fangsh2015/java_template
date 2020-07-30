package concurrent.future.completable_future;

/**
 * 费率服务
 * Created by Niki on 2019/6/13 18:06
 */
public class ExchangeDemo {
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static double getRate(String source,String target){
        delay();
        return 10;
    }
}
