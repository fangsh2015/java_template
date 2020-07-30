package algorithm.sample;

/**
 * 斐波那契函数
 * 1.1.2.3.5.8 。。。
 * Created by Niki on 2018/5/3 13:40
 */
public class Fibonacci {

    /**
     * 非递归方式的斐波那契函数
     * @param n
     * @return
     */
    private static int fibonancci(int n){
        if (n == 1 || n == 2) {
            return 1 ;
        }
        int begin_1 = 1 ;
        int begin_2 = 1 ;
        int res = 0 ;
        for (int i = 3; i <= n; i++) {
            res = begin_1 + begin_2 ;
            begin_1 = begin_2 ;
            begin_2 = res ;
        }
        return res ;
    }

    /**
     * 采用递归的方式，查询第n为的斐波那契值
     * @param n
     * @return
     */
    private static int fibonancci_recursion(int n){
        if (n == 1 || n == 2) {
            return 1 ;
        }

        return fibonancci_recursion(n-1)+fibonancci(n-2) ;
    };

    public static void main(String[] args) {
        System.out.println(fibonancci(5));
        System.out.println(fibonancci_recursion(7));
    }
}
