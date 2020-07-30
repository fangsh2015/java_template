package j8.date;

import java.time.Clock;

/**
 * Java8中使用时钟的Demo
 * 可以使用clock来代替System.currentTimeInMills()与TimeZone.getDefault()
 * Created by Niki on 2018/2/12 14:18
 */
public class ClockDemo {
    //某个时区下当前的瞬时时间，日期，时间
    public static void clock(){
        Clock clock = Clock.systemUTC();
        System.out.println("Clock:"+clock);

        Clock.systemDefaultZone();
        System.out.println("Clock:"+clock);
    }

    public static void main(String[] args) {
        clock();
    }
}
