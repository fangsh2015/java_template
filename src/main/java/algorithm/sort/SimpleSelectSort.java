package algorithm.sort;

/**
 * 简单选择排序
 *
 * @author 80002165 @date 2017年4月12日 上午10:09:11
 * 思想：对个数为n的记录数，进行n-1选择，每次在n-i+1(i=1,2...n-1)个记录中选择关键字最小的记录作为有效序列中的第i个记录
 * 类似于，每次选择一个（最小，或最大值）将其顺序排列
 */
public class SimpleSelectSort {
    public static <T extends Comparable<T>> T[] simpleSelectSort(T[] arrs, int comparator) {
        SortUtils.Assert(arrs);
        int arrLen = arrs.length;
        int flag = 0;
        while (flag < arrLen) {
            for (int i = flag + 1; i < arrs.length; i++) {
                if (arrs[i].compareTo(arrs[flag]) == comparator) {
                    SortUtils.swap(arrs, i, flag);
                }
            }
            flag++;
        }
        return arrs;
    }

    /**
     * 优化简单排序方法，减少数组交换的次数，每次比较（如果有交换）只进行一次数组交换
     *
     * @param arrs
     * @param comparator
     * @return
     * @author 80002165 @date 2017年4月12日 上午10:34:52
     */
    public static <T extends Comparable<T>> T[] optimizeSimpleSelectSort(T[] arrs, int comparator) {
        SortUtils.Assert(arrs);
        int arrLen = arrs.length;
        int flag = 0;
        while (flag < arrLen) {
            int mark = flag;
            for (int i = flag + 1; i < arrs.length; i++) {
                if (arrs[i].compareTo(arrs[mark]) == comparator) {
                    mark = i;
                }
            }
            if (mark != flag) {
                SortUtils.swap(arrs, flag, mark);
            }
            flag++;
        }
        return arrs;
    }

    public static void main(String[] args) {
        Integer[] arrs = {1, 5, 3, 9, 2, 0, 6};
        optimizeSimpleSelectSort(arrs, 1);
        SortUtils.printArr(arrs);
    }
}
