package algorithm.sort;


/**
 * 希尔排序（分组插入排序，缩小增量排序）
 * @author 80002165 @date 2017年4月13日 上午9:07:59
 */
/**
 * 算法思路：
 * 希尔排序是对直接排序的升级版，希尔排序按照步长（gap）分组，对每组记录采用直接插入排序法进行排序
 */
/**
 * 算法演示：10个元素，步长gap为2
 * 49   38   65   97   26   13   27   49   55   04
 第一趟（10/2=5）
 * 1A                       1B
 *
 *      2A                       2B

 *           3A                        3B

 *                4A                        4B

 *                      5A                       5B
 * 13   27   49   55   04   49   38   65   97   26
 第二趟（5/2=2）
 * 1A        1B        1C        1D        1E
 *      2A        2B        2C        2D        2E
 * 04   26   13   27   38   49   49   55    97  65
 第三趟 （2/2 = 1）这是就是插入排序了
 04   13   26   27   38   49   49   55    65  97
 */
/**
 * 算法性能：
 */
/**
 * 时间复杂度：
 * 最坏情况：步长：n/2^i时间复杂度：O(n^2); 步长：n/2^i时间复杂度：O(n^2); 步长：2^k-1时间复杂度：O(n^3/2);
 */
/**
 * 算法稳定性：
 * 该算法不稳定
 */
/**
 直接插入排序是稳定的；而希尔排序是不稳定的。

 直接插入排序更适合于原始记录基本有序的集合。

 希尔排序的比较次数和移动次数都要比直接插入排序少，当N越大时，效果越明显。

 在希尔排序中，增量序列gap的取法必须满足：最后一个步长必须是 1 。

 直接插入排序也适用于链式存储结构；希尔排序不适用于链式结构。
 */

public class ShellSort {
    /**
     *
     * @author 80002165 @date 2017年4月14日 上午9:44:36
     * @param arrs
     *        待排序数组
     * @param comparator
     *        比较器
     * @param gapNum
     *        根据步长分的组数
     * @return
     */
    private static <T extends Comparable<T>> T[] gapInsertSort(T[] arrs, int comparator, int gapNum) {
        for (int i = 0; i < gapNum; i++) {
            for (int j = i; j < arrs.length; j += gapNum) {
                if (j + gapNum >= arrs.length)
                    continue ;
                // if (arrs[j + gapNum].compareTo(arrs[j]) == comparator) {
                // 在这里一直向前进行比较
                int flag = j ;
                while (flag >= 0) {
                    if (arrs[flag + gapNum].compareTo(arrs[flag]) == comparator) {
                        SortUtils.swap(arrs, flag, flag + gapNum) ;
                    }
                    flag -= gapNum ;
                }
                // }
            }
        }
        return arrs ;
    }

    /**
     * 希尔排序
     * @author 80002165 @date 2017年4月14日 上午9:57:35
     * @param arrs
     *        待排序数组
     * @param comparator
     *        比较器
     * @param gap
     *        步长
     * @return 排序后的数组
     */
    private static <T extends Comparable<T>> T[] shellSort(T[] arrs, int comparator, int gap) {
        SortUtils.Assert(arrs) ;
        int len = arrs.length ;
        int gapNum = len / gap ;

        while (gapNum >= 1) {
            arrs = gapInsertSort(arrs, comparator, gapNum) ;
            gapNum /= gap ;
        }
        return arrs ;
    }

    /**
     * 希尔排序的代码优化，减少数组交换次数
     * @author 80002165 @date 2017年4月15日 上午9:00:13
     * @param arrs
     * @param comparator
     * @param gap
     * @return
     */
    private static <T extends Comparable<T>> T[] shellSort2(T[] arrs, int comparator, int gap) {
        SortUtils.Assert(arrs) ;
        int i, j, gapNum ;
        for (gapNum = arrs.length / gap; gapNum > 0; gapNum /= gap)
            // 求每一次运算元素分组的个数 {
            for (i = 0; i < gapNum; i++) {// 对数组中元素进行分组
                for (j = i + gapNum; j < arrs.length; j += gapNum) {
                    /*
                     * 假设从小到大排序，则如果前面的元素比后面元素大，则开始对这一组前面所有的元素进行插入排序
                     * 注意是所有元素，并不是相邻元素交换位置
                     */
                    if (arrs[j].compareTo(arrs[j - gapNum]) == comparator) {
                        /*
                         * 对步长是gapNum的数组进行直接插入排序
                         */
//                        T temp = arrs[j] ;
//                        int k = j - gapNum ;
//                        while (k >= 0 && arrs[k].compareTo(temp) != comparator) {
//                            arrs[k+gapNum] = arrs[k] ;
//                            k -= gapNum ;// 这里就要k>=gapNum了，而不是从0开始；因为这样就不会数组越界
//                        }
//                        arrs[k+gapNum] = temp ;
                        T temp = arrs[j] ; //记录下这个值
                        int k ;
                        for(k = j-gapNum ; k >=0 && arrs[k].compareTo(temp) != comparator; k-= gapNum){
                            arrs[k+gapNum] = arrs[k] ;
                        }
                        arrs[k+gapNum] = temp ;

                    }
                }

            }
        return arrs ;
    }

    public static void main(String[] args) {
        Integer[] arrs = { 49, 38, 65, 97, 26, 13, 27, 49, 55, 4 } ;
        // Integer[] arrs = { 13, 27, 49, 55, 4, 49, 38, 65, 97, 26 } ;
        ShellSort.shellSort2(arrs, -1, 2) ;
        SortUtils.printArr(arrs) ;
    }
}
