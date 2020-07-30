package j8.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Niki on 2018/2/26 10:09
 */
public class CalendarDemo {
    //获取一年中的第几天
    public static void getWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置一周的第一天为星期一，美国周日为一周开始
        int i = calendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println("今天是今年的第"+i+"周");
    }

    public static void test(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,2,26);
        calendar.setTime(new Date());
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int i = calendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println(i);
    }
    public static void test2(){
        LocalDate localDate = LocalDate.of(2010, 1,3);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //calendar.setFirstDayOfWeek(Calendar.MONDAY);

        int i = calendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println(i);
    }
    public static void test(String... test){
        for(String str : test){
            System.out.println(str);
        }
    }
    public static void main(String[] args) {
//        getWeek();
//        test2();
        test("fang","shu","hao");
    }
}
