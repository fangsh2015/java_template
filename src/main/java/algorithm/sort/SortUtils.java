package algorithm.sort;

/**
 * 排序算法的公用方法
 * Created by Niki on 2018/5/3 13:52
 */
public class SortUtils {
    /**
     * 数组位置为i和j的元素互换
     * @author 80002165 @date 2017年4月11日 上午11:12:35
     * @param arrs
     * @param i
     * @param j
     * @return
     */
    public static <T> T[] swap(T[] arrs, int i, int j) {
        T temp = arrs[i] ;
        arrs[i] = arrs[j] ;
        arrs[j] = temp ;
        return arrs ;
    }

    public static <T extends Comparable<T>> void Assert(T[] arrs) {
        if (arrs == null || arrs.length <= 0)
            throw new IllegalArgumentException("输入的数组为null！") ;
    }

    /**
     * 打印数组
     * @param arrs
     * @param <T>
     */
    public static <T> void printArr(T[] arrs) {
        int index = 0 ;
        for (T t : arrs) {
            System.out.print(t) ;
            if (index < arrs.length - 1)
                System.out.print(",") ;
            index++ ;
        }
    }
}
