package algorithm.sort;

/**
 * 冒泡排序算法 对相邻元素进行比较，将较大的元素往后移 最差的时间复杂度为O(n^2)，最好时间复杂度为O(n)
 * @author 80002165 @date 2017年4月11日 上午10:24:48
 */
public class BubbleSort {
    /**
     * 正常冒泡排序，N个元素进行N-1次大比较，每次比较都是相邻元素进行比较
     *
     * @author 80002165 @date 2017年4月11日 上午10:36:12
     * @param arrs
     * @return
     */
    public static <T extends Comparable<T>> T[] bubbleSort(T[] arrs, int compare) {
        SortUtils.Assert(arrs) ;
        for (int i = 0; i < arrs.length - 1; i++) {
            for (int j = 0; j < arrs.length - i - 1; j++) {
                if (arrs[j].compareTo(arrs[j + 1]) == compare) {
                    SortUtils.swap(arrs, j, (j + 1)) ;
                }
            }
        }
        return arrs ;
    }

    /**
     * 冒泡排序的变形，每次排序确定一个位数上的值 (n-1)+(n-2)+...+(n-(n-1))
     * @author 80002165 @date 2017年4月11日 上午10:42:00
     * @param arrs
     * @return
     */
    public static <T extends Comparable<T>> T[] bubbleSort2(T[] arrs, int compare) {
        SortUtils.Assert(arrs) ;
        for (int i = 0; i < arrs.length; i++) {
            for (int j = i + 1; j < arrs.length; j++) {
                SortUtils.swap(arrs, j, (j + 1)) ;
            }
        }
        return arrs ;
    }

    /************************* 优化冒泡排序 **************************************/
    /**
     * 优化冒泡算法，当进行某次表后，都没有进行排序，说明排序已经完成，则不再需要进行下面的冒泡排序了
     * 这次优化，仅优化最外面的排序次数，每次大排序里面的两两比较还是要进行完成才决定是不是要进行下一次大排序
     * @author 80002165 @date 2017年4月11日 上午11:21:03
     * @param arrs
     * @param compare
     * @return
     */
    public static <T extends Comparable<T>> T[] optimizeBubbleSortOptimize(T[] arrs, int compare) {
        SortUtils.Assert(arrs) ;
        int arrLen = arrs.length ;
        boolean sortFlag = true ;
        int sortNum = 0 ; //比较的次数
        while (sortFlag) {
            sortFlag = false ;
            for (int j = 1; j < arrLen; j++) {
                if (arrs[j - 1].compareTo(arrs[j]) == compare) {
                    SortUtils.swap(arrs, (j - 1), j) ;
                    sortFlag = true ;
                }
                sortNum++ ;
            }
            arrLen-- ;
        }
        System.out.println("排序次数：" + sortNum) ;
        return arrs ;
    }

    public static <T extends Comparable<T>> T[] optimizeBubbleSortOptimize2(T[] arrs, int compare) {
        SortUtils.Assert(arrs) ;
        int arrLen = arrs.length ;
        int flag = arrLen ;
        int k, j ;
        int sortNum = 0 ;
        while (flag > 0) {
            k = flag ;
            flag = 0 ;
            for (j = 1; j < k; j++) {
                if (arrs[j - 1].compareTo(arrs[j]) == compare) {
                    SortUtils.swap(arrs, (j - 1), j) ;
                    flag = j ;
                }
                sortNum++ ;
            }
        }
        System.out.println("排序次数：" + sortNum) ;
        return arrs ;
    }

    public static void main(String[] args) {
        Integer[] arrs = { 1, 5, 3, 9, 2, 0, 6 } ;
        Integer[] arrs2 = { 4, 2, 1, 5, 3, 6, 7, 8, 9 } ;
        SortUtils.printArr(optimizeBubbleSortOptimize(arrs2, 1)) ;
        Integer[] arrs22 = { 4, 2, 1, 5, 3, 6, 7, 8, 9 } ;
        SortUtils.printArr(optimizeBubbleSortOptimize2(arrs22, 1)) ;
    }
}
