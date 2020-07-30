package j8.date;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Niki on 2018/2/12 13:56
 */
public class LocalTimeDemo {
    private static LocalTime now = LocalTime.now();
    //获取当前时间
    public static void getLocalTime(){
        LocalTime localTime = LocalTime.now();
        System.out.println("当前时间为：" + localTime.toString());
        System.out.println("当前小时为："+localTime.getHour());
        System.out.println("当前分为："+localTime.getMinute());
        System.out.println("当前秒为："+localTime.getSecond());
    }
    //增加时间里面的小时数
    public static void addHour(){
        LocalTime localTime = LocalTime.now();
        localTime =  localTime.plusHours(2);
        System.out.println("两小时后："+localTime.toString());
        localTime = localTime.minusHours(2).plusMinutes(30);
        System.out.println("30分钟以后："+localTime.toString());
    }

    public static void addHour2(){
        LocalTime localTime = now.plus(1, ChronoUnit.HOURS);
        System.out.println("一小时后："+ localTime.toString());
        System.out.println("10分钟后："+now.plus(10, ChronoUnit.MINUTES));
        System.out.println("6分钟前："+now.minus(6,ChronoUnit.MINUTES));
    }

    public static void main(String[] args) {
//        getLocalTime();
        addHour2();
    }

}
