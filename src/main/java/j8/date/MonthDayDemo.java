package j8.date;

import java.time.MonthDay;

/**
 * 用来表示某月某天特定日期。例如生日，信用卡还款日等固定的日期
 * Created by Niki on 2018/2/26 9:16
 */
public class MonthDayDemo {
    static MonthDay currentMonthDay = MonthDay.now();
    public static void monthDay(){
        System.out.println("当前日期为："+ currentMonthDay.toString());
    }

    public static void compareMonthDay(){
        MonthDay monthDay = MonthDay.of(3,18);
        int compare = currentMonthDay.compareTo(monthDay);
        if(compare == 0){
            System.out.println("两个日期相同！");
        }else if(compare == -1){
            System.out.println("3.18在当前日期之后！");
        }else if(compare == 1){
            System.out.println("3.18在当前日期之前了！");
        }
    }
    public static void main(String[] args) {
        compareMonthDay();
    }
}
