package String;

import java.util.function.Function;

/**
 * 测试concat和+凭借字符串的性能
 * Created by Niki on 2018/1/19 11:23
 */
public class ConcatAndAddDemo {
    public static String concatStr(String a, String b){
        return  a.concat(b);
    }

    public static String addStr(String a, String b){
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(b);
        sb.toString();
        return a+b;
    }

    public static void test(boolean is){
        String temp = "";
        long begin = System.currentTimeMillis();
        for(int i=0; i<1000000; i++){
            if(is)
                temp = concatStr(temp,i+"");
            else
                temp = addStr(temp, i+"");
        }
        long end = System.currentTimeMillis();
        System.out.println("拼接"+temp+"， 用时："+(end-begin));
    }

    public static void main(String[] args) {
        test(true);
        test(false);
    }
}
