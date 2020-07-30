package j8.date;

import java.time.YearMonth;

/**
 * 表示固定日期，表示类似信用卡到期日期这样的某年的某月
 * 并且可以计算得到某年的天数以及某月的天数，计算某一年2月份有几天
 * Created by Niki on 2018/2/26 9:16
 */
public class YearMonthDemo {
    private static void yearMonthDemo(){
        YearMonth currentYearMonth = YearMonth.now();
        System.out.println(currentYearMonth.toString());
        System.out.println(currentYearMonth.lengthOfYear());
        System.out.println(currentYearMonth.lengthOfMonth());
        System.out.println(currentYearMonth.getYear());
        System.out.println(currentYearMonth.getMonth());
        System.out.println(currentYearMonth.getMonthValue());
    }

    private static void yearLength(){
        YearMonth yearMonth  = YearMonth.of(2019,2);
        int yearLength = yearMonth.lengthOfYear();
        System.out.println("2019年共有："+yearLength+"天！");
    }

    private static void monthLength(){
        YearMonth yearMonth = YearMonth.of(2010,2);
        int monthLength = yearMonth.lengthOfMonth();
        System.out.println("2010年2月份有："+monthLength+"天！");
    }
    public static void main(String[] args) {
        yearMonthDemo();
        yearLength();
        monthLength();
    }
}
