package Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 因为java8以前提供的日期格式化类 DateFormat SimpleDateFormat都是线程不安全的，提供一个线程安全的日期格式化工具类
 */
public class ThreadSelfDateFormUtil {
    private static ThreadLocal<SimpleDateFormat> formatThreadLocal = new ThreadLocal<>() ;

    public static  SimpleDateFormat getFormat(){
        SimpleDateFormat format = formatThreadLocal.get() ;
        if(format == null){
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
            formatThreadLocal.set(format);
        }
        return format ;
    }

    public static String format(Date date){
        return getFormat().format(date) ;
    }

    public static Date parse(String  date) throws ParseException {
        return getFormat().parse(date) ;
    }

    public void test(String name){
        System.out.println("hello "+name);
    }



}
