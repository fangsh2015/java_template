package j8.date;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;

/**
 * 获取时间戳
 * Created by Niki on 2018/2/26 9:39
 */
public class InstantDemo {
    public static void instantDemo(){
        Instant timestamp = Instant.now();
        System.out.println(timestamp.toString());
    }

    public static void instant(int year, int month, int day){
        LocalDate localdate = LocalDate.of(year,month,day);

        Instant instant = Instant.from(LocalDateTime.now());
        System.out.println(instant.toString());
    }

    public static void main(String[] args) {
        instantDemo();
        //instant(2018,10,26);
    }
}

