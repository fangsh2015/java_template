package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期处理万能工具类
 * <p>Title: JodaTimeDemo</p>
 * @author  Liyan
 * @date    2017年10月26日 下午2:15:09
 */
@Slf4j
public class JodaTimeDemo {
    /**
     * 判断日期是否过期
     * @param datetimeRented 需要判断的日期
     * @param pastDay 过期天数
     * @return
     */
    public static boolean isRentalOverdueDay(DateTime datetimeRented, int pastDay) {
        Period rentalPeriod = new Period().withDays(pastDay);
        return datetimeRented.plus(rentalPeriod).isBeforeNow();
    }

    public static void calculateExpirationTime(int pastDay) {
        DateTime now = new DateTime(new Date());

        String beginDay = now.minusDays(pastDay).toString("yyyy-MM-dd HH:mm:ss");
        now.minusDays(pastDay).toDate();
        System.out.println(beginDay);
    }
    public static void main(String[] args) {
//        DateTime create = DateTime.parse("2018-10-20 12:00:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(isRentalOverdueDay(create,20));
//
//        System.out.println(DateTime.now().minusDays(20).toString("yyyy-MM-dd HH:mm:ss"));

        calculateExpirationTime(10);

        System.out.println(DateTime.now().plusDays(180).toString("yyyy-MM-dd"));
    }
}