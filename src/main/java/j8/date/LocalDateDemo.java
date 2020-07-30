package j8.date;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * java8中新的时间API使用demo
 * LocalDate不包含具体时间的日期类。
 * Created by Niki on 2018/2/11 15:36
 */
public class LocalDateDemo {
    private static LocalDate now = LocalDate.now();
    // 获取当天日期
    public static void getLocalDate(){
        LocalDate localDate = LocalDate.now();
        System.out.println("当前日期为："+localDate.toString());
    }

    //获取当前的年月日
    public static void getYMD(){
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        System.out.println("当前年份："+year);
        System.out.println("当前月份："+month);
        System.out.println("当前天："+day);
    }

    //获取某个特定的日期
    public static void toLocalDate(){
        LocalDate localDate = LocalDate.of(2018,3,21);
        System.out.println(localDate.toString());

        localDate = LocalDate.parse("2018-04-12");
        System.out.println(localDate.toString());
    }

    //检查两个日期是否相等
    public static void compareLocalDate(){
        LocalDate ld1 = LocalDate.of(2018, 5,1);
        LocalDate ld2 = LocalDate.parse("2018-10-01");
        System.out.println(ld1.equals(ld2));

        LocalDate now = LocalDate.now();
        LocalDate now2 = LocalDate.parse("2018-02-11") ;
        System.out.println(now.equals(now2));
    }

    //检查重复事件，比如生日。某些月日结合的天与年份无关，比如说圣诞节，生日
    public static void monthAndDay(){
        LocalDate dateOfBirth = LocalDate.of(2010,2,11);
        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());

        MonthDay currentMonthDay = MonthDay.from(LocalDate.now());

        if(currentMonthDay.equals(birthday)){
            System.out.println("今天是你的生日");
        }else{
            System.out.println("今天不是你的生日");
        }
    }

    //一周以后
    public static void plusWeek(){
        LocalDate localDate = now.plus(1, ChronoUnit.WEEKS);
        System.out.println("一周后日期为："+localDate.toString());
    }

    //一月以后
    public static void plusMonth(){
        LocalDate localDate = now.plus(1, ChronoUnit.MONTHS);
        System.out.println("一月后日期为："+localDate.toString());
    }

    //一年以后
    public static void plusYear(){
        LocalDate localDate = now.plus(1, ChronoUnit.YEARS);
        System.out.println("一年后日期为："+localDate.toString());

    }

    //判断日期的前后
    public static void after(){
        LocalDate localDate = LocalDate.of(2019,3,18);
        if(localDate.isBefore(now)){
            System.out.println("日期在今天之前");
        }
        if(localDate.isAfter(now))
            System.out.println("日期在今天之后");
    }

    public static void before(){

    }

    //一年中的第几周
    public static void getWeek(){
        IsoChronology isoChronology = now.getChronology();
        System.out.println(now.getDayOfWeek());
    }

    public static void main(String[] args) {
//        getLocalDate();
//        getYMD();
//        toLocalDate();
//        compareLocalDate();
//        monthAndDay();
//        plusWeek();
//        plusMonth();
//        plusYear();
//        after();
        getWeek();
    }
}
