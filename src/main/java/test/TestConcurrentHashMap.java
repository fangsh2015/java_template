package test;

/**
 * Created by Niki on 2018/2/12 9:19
 */
public class TestConcurrentHashMap {
    public static void main(String[] args) {
        int concurrentLevel =30 ;

        int size = 1;
        int sshift =0;
        while(size < concurrentLevel){
            ++sshift;
            size <<=1;
        }
        System.out.println(size);
        System.out.println(sshift);
    }
}
