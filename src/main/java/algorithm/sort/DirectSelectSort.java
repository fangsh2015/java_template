package algorithm.sort;

/**
 * 直接选择排序 算法思路： 将待排序数组分为【有序区】和【无序区】。选择无序区最小的值（从小到大排序）直接插入到有序区的最后面（无序区的开始位置）
 * 算法演示：
 *
 * 算法性能：
 *
 * 时间复杂度：
 *
 * 算法稳定性：
 *
 * @author 80002165 @date 2017年4月17日 上午9:17:04
 */
public class DirectSelectSort {
    public static <T extends Comparable<T>> T[] directSelectSort(T[] arrs, int compartor) {
        SortUtils.Assert(arrs) ;
        int i ,j ;
        for(i = 0; i<arrs.length;i++){
            int swapIndex = i ;
            for(j = i+1; j<arrs.length;j++){
                if(arrs[j].compareTo(arrs[swapIndex]) == compartor){
                    swapIndex = j ;
                }
            }
            if(swapIndex != i){
                SortUtils.swap(arrs, i, swapIndex) ;
            }
        }
        return arrs ;
    }

    public static void main(String[] args) {
        Integer[] arrs = { 1, 5, 3, 9, 2, 0, 6 } ;
        SortUtils.printArr(directSelectSort(arrs, -1));
    }
}
