package algorithm.sort;

/**
 * 直接插入排序
 * @author 80002165 @date 2017年4月12日 上午10:45:03
 *
 */
public class DirectInsertionSort {
    public static <T extends Comparable<T>> T[] directInsertionSort(T[] arrs, int comparator) {
        SortUtils.Assert(arrs) ;
        for (int i = 1; i < arrs.length; i++) {
            for (int j = 0; j < i; j++) {// 与拍好序的数组进行比较
                if (arrs[j].compareTo(arrs[i]) != comparator) {
                    // 从j开始到i结束，都往后移动一位给j空出数据位
                    for (int flag = i; flag > j; flag--) {
                        SortUtils.swap(arrs, flag - 1, flag) ;
                    }
                }
            }
        }
        return arrs ;
    }
    /**
     * 优化，减少数组互换时需要的一个临时变量，从而减少所需的内存空间
     * @author 80002165 @date 2017年4月15日 上午10:20:25
     * @param arrs
     * @param comparator
     * @return
     */
    public static <T extends Comparable<T>> T[] directInsertionSort22(T[] arrs, int comparator) {
        SortUtils.Assert(arrs) ;
        for (int i = 1; i < arrs.length; i++) {
            for (int j = 0; j < i; j++) {// 与拍好序的数组进行比较
                if (arrs[j].compareTo(arrs[i]) != comparator) {
                    // 从j开始到i结束，都往后移动一位给j空出数据位
                    T temp = arrs[i] ;
                    int flag ;
                    for (flag = i; flag > j; flag--) {
//                        SortUtils.swap(arrs, flag - 1, flag) ;
                        /*
                         * 在这里进行优化，不对数组的元素通过中间元素彼此交换，而是通过一个变量记录，数组整体向后移的方式
                         */
                        arrs[flag] = arrs[flag-1] ;
                    }
                    arrs[flag] = temp ;
                }
            }
        }
        return arrs ;
    }

    public static <T extends Comparable<T>> T[] directInsertionSort2(T[] arrs, int comparator) {
        SortUtils.Assert(arrs) ;
        int i, j ;
        for (i = 1; i < arrs.length; i++) {
            for (j = i - 1; j >= 0; j--) {
                if (arrs[j].compareTo(arrs[i]) == comparator) {
                    break ;
                }
            }
            if (j != i - 1) {
                for (int k = i - 1; k > j; k--) {
                    SortUtils.swap(arrs, k, k + 1) ;
                }
            }
        }
        return arrs ;
    }

    /**
     * 直接插入排序优化
     *
     * @author 80002165 @date 2017年4月12日 下午5:42:19
     * @param arrs
     * @param comparator
     * @return
     */
    public static <T extends Comparable<T>> T[] optimizeDirectInsertionSort(T[] arrs, int comparator) {
        SortUtils.Assert(arrs) ;
        int i, j ;
        int n = arrs.length ;
        for (i = 1; i < n; i++)
            /*
             * 假设从小到大排序，后面的值A比前面相邻的值B大，则将A插入到0-B这些已排好序的数组中
             */
            if (arrs[i].compareTo(arrs[i - 1]) == comparator) {
                //记录A的值
                T temp = arrs[i] ;
                /*
                 * 数组从B开始往前遍历到0，每个值和A比较一遍，看是否比A大，如果比A大，则将数往前移动一位 arrs[j+1] = arrs[j]
                 * 这样的话，比较到最后比temp大的数（假设位数为n）就重复了，此时j数组的下表为n-1。所以将记录的temp赋值给arrs[j+1]
                 * 在这里最重要的是要注意数组越界的问题。因为是自减。所以j最好是从i-1开始
                 */
                for (j = i - 1; j >= 0 && !(arrs[j].compareTo(temp) == comparator); j--){
                    arrs[j + 1] = arrs[j] ;
                }
                arrs[j + 1] = temp ;
            }
        return arrs ;
    }

    public static <T extends Comparable<T>> T[] optimizeDirectInsertionSort2(T[] arrs, int comparator) {
        SortUtils.Assert(arrs) ;
        for (int i = 1; i < arrs.length; i++) {
            for (int j = i - 1; j >= 0 && (arrs[j+1].compareTo(arrs[j]) == comparator); j--) {
                SortUtils.swap(arrs, j, j + 1) ;
            }
        }
        return arrs ;
    }

    public static void main(String[] args) {
        Integer[] arrs = { 1, 5, 4, 9, 2, 3, 6 } ;
        directInsertionSort22(arrs, -1) ;
        SortUtils.printArr(arrs) ;
    }
}
