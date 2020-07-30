package algorithm.sort;

/**
 * 归并排序
 * @author 80002165 @date 2017年4月18日 上午9:05:25
 */
/**
 * 算法思路：
 *
 */
/**
 * 算法演示：
 */
/**
 * 算法性能：
 */
/**
 * 时间复杂度：
 * 最坏情况：
 * 最好情况：
 */
/**
 * 空间复杂度：
 * 最坏情况：
 * 最好情况：
 */
/**
 * 算法稳定性：稳定排序算法
 *
 */
public class MergeSort {

    /**
     * 将两个有序数组合并
     * @author 80002165 @date 2017年4月18日 下午8:21:52
     * @param arr1
     *        有序数组1
     * @param arr2
     *        有序数组2
     * @param target
     *        合并后的目标数组 长度为arr1和arr2的和
     * @param comparator
     *        大小排序标识，应该与arr1和arr2的标识一致
     * @return
     */
    private static <T extends Comparable<T>> T[] mergeOrderArrays(T[] arr1, T[] arr2, T[] target, int comparator) {
        SortUtils.Assert(arr1) ;
        SortUtils.Assert(arr2) ;
        SortUtils.Assert(target) ;
        int i, j ;
        int targetIndex = 0 ;
        // 直到一个数组结束为止
        for (i = 0, j = 0; i < arr1.length && j < arr2.length;) {
            T temp1 = arr1[i] ;
            T temp2 = arr2[j] ;

            if (temp1.compareTo(temp2) == comparator) {
                target[targetIndex++] = temp1 ;
                i++ ;
            } else {
                target[targetIndex++] = temp2 ;
                j++ ;
            }
        }

        // 找到没有结束的数组，全部插入到target的剩余为止
        if (i == arr1.length) {// 数组1到结尾
            System.arraycopy(arr2, j, target, i + j, arr2.length - j) ;
        } else if (j == arr2.length) {// 数组2到结尾
            System.arraycopy(arr1, i, target, i + j, arr1.length - i) ;
        }
        return target ;
    }

    private static <T extends Comparable<T>> T[] mergeOrderArrays2(T[] arr1, T[] arr2, T[] target, int comparator) {
        SortUtils.Assert(arr1) ;
        SortUtils.Assert(arr2) ;
        SortUtils.Assert(target) ;
        int i, j ;
        int targetIndex = 0 ;
        for (i = 0, j = 0; i < arr1.length && j < arr2.length;) {
            if (arr1[i].compareTo(arr2[j]) == comparator) {
                target[targetIndex++] = arr1[i++] ;
            } else {
                target[targetIndex++] = arr2[j++] ;
            }
        }
        while (i < arr1.length) {
            target[targetIndex++] = arr1[i++] ;
        }
        while (j < arr2.length) {
            target[targetIndex++] = arr2[j++] ;
        }
        return target ;
    }

    /**
     * 归并排序
     * @author 80002165 @date 2017年4月18日 下午8:56:44
     * @param arrs
     * @param target
     * @param comparator
     * @return
     */
    public static <T extends Comparable<T>> T[] mergeSort(T[] arrs, T[] target, int comparator) {
        SortUtils.Assert(arrs) ;
        SortUtils.Assert(target) ;

        return target ;
    }

    public static void main(String[] args) {
        int[] arr1 = {4, 3, 9, 1, 2, 5, 6} ;
        // Integer[] arr2 = { 4, 5 } ;
        // Integer[] target = new Integer[7] ;
        // mergeOrderArrays2(arr1, arr2, target, -1) ;
        // SortUtils.printArr(target) ;

        int[] a = new int[] { 4, 3, 9, 1, 2, 5, 6} ;
        mergeSort_temp(a, -1, 1);

        for (int i = 0; i < a.length; ++i) {
            System.out.print(a[i] + "　") ;
        }
    }

    // 　private　static　long　sum　=　0;

    /**
     * *　
     *
     * <pre>
     * 　*　二路归并
     * 　*　原理：将两个有序表合并和一个有序表
     * 　*　
     * </pre>
     *
     * 　* 　*　@param　a 　*　@param　s 　*　第一个有序表的起始下标 　*　@param　m 　*　第二个有序表的起始下标
     * 　*　@param　t 　*　第二个有序表的结束小标 　*
     */
    private static void merge(int[] a, int s, int m, int t) {
        int[] tmp = new int[t - s + 1] ;
        int i = s, j = m, k = 0 ;
        while (i < m && j <= t) {
            if (a[i] <= a[j]) {
                tmp[k] = a[i] ;
                k++ ;
                i++ ;
            } else {
                tmp[k] = a[j] ;
                j++ ;
                k++ ;
            }
        }
        while (i < m) {
            tmp[k] = a[i] ;
            i++ ;
            k++ ;
        }
        while (j <= t) {
            tmp[k] = a[j] ;
            j++ ;
            k++ ;
        }
        System.arraycopy(tmp, 0, a, s, tmp.length) ;
    }

    /**
     * 　* 　*　@param　a 　*　@param　s 　*　@param　len 　*　每次归并的有序集合的长度
     */
    public static void mergeSort(int[] a, int s, int len) {
        int size = a.length ;
        int mid = size / (len << 1) ;
        int c = size & ((len << 1) - 1) ;
        // 　-------归并到只剩一个有序集合的时候结束算法-------//
        if (mid == 0)
            return ;
        // 　------进行一趟归并排序-------//
        for (int i = 0; i < mid; ++i) {
            s = i * 2 * len ;
            merge(a, s, s + len, (len << 1) + s - 1) ;
        }
        // 　-------将剩下的数和倒数一个有序集合归并-------//
        if (c != 0)
            merge(a, size - c - 2 * len, size - c, size - 1) ;
        // 　-------递归执行下一趟归并排序------//
        mergeSort(a, 0, 2 * len) ;
    }

    /**
     * T[] temp = new T[(end1-begin1+1)*2]
     * @author 80002165 @date 2017年4月19日 下午5:22:08
     * @param arrs
     * @param begin1
     * @param end1
     * @param begin2
     * @param end2
     * @return
     */
    private static  int[] mergeOrderArrays(int[] arrs, int begin1, int end1, int begin2, int end2, int[] temp) {
        /** i为第一个数组的指针，j为第二个数组的指针，index为合并后数组的指针 */
        int i, j, index=0 ;
        i = begin1 ;
        j = begin2 ;

        /** 将数组1和数组2排序存放到合并后数组temp里 */
        while (i <= end1 && j <= end2) {
            if (arrs[i]<arrs[j]) {
                temp[index++] = arrs[i++] ;
            } else {
                temp[index++] = arrs[j++] ;
            }
        }

        /** 将没有移动到最后的那个数组，全部拷贝到temp里面 */
        while(i<=end1){
            temp[index++] = arrs[i++] ;
        }
        while(j<=end2){
            temp[index++] = arrs[j++] ;
        }
        //将temp数组拷贝到arrs里面，相当于对arrs重新排序
        System.arraycopy(temp, 0, arrs, begin1, temp.length);
        return arrs ;
    }

    public static int[] test_mergeSort(int[] arrs, int comparator, int index){
        /** 每次归并有序数组元素个数，从1开始，因为1个元素的数组一定是有序的。递增规则为2^0 , 2^1 , 2^2 ... */
        int len = arrs.length ;
        /**每次比数组个数为len*/
//        int num = 1<<index ;
        /**每次比较的总元素个数为index的两倍*/
        int num = index << 1 ;
        /**数组分成几组*/
        int group = len / num ;

        if(group == 0)
            return arrs ;

        int begin1,end1,begin2,end2=1 ;
        int eLen = 1<<(index-1) - 1 ;
        for(int i=0; i<group; i++){
            begin1 = index*i*2 ;
            end1 = begin1+(index-1) ;
            begin2 = begin1 + index ;
            end2 = begin2 + (index-1) ;
            int[] tempArr = new int[2*index] ;
            arrs = mergeOrderArrays(arrs, begin1, end1, begin2, end2, tempArr) ;
        }

        //归并排序

        //没有均分，将剩下的数与最后一组序列归并排序
        if(end2 != len-1){
            //最后一次如果剩下的元素不止一个，则需要对剩下的元素先归并排序，再将剩下的元素与之前所有的元素组成的有序进行归并排序
            if(group/2 == 0 && end2 != len-2){
                //先将所剩下的数组复制出来
//                Object[] allSurplus = new Object[len-1-end2] ;
//                int surplusIndex = 0 ;
//                for(int surplus = end2+1; surplus < len;surplus++){
//                    allSurplus[surplusIndex++] = arrs[surplus] ;
//                }
                int[] allSurplus = new int[len-1-end2] ;
                System.arraycopy(arrs, end2+1, allSurplus, 0, len-1-end2);
                allSurplus = test_mergeSort(allSurplus, comparator, 1);
                System.arraycopy(allSurplus, 0, arrs, end2+1, allSurplus.length);
            }
            int[] tempArr = new int[len] ;
            //排好序后最后一个元素下表
            int allOrderIndex = 2*index*group-1 ;
            arrs = mergeOrderArrays(arrs, 0, allOrderIndex,allOrderIndex+1,len-1,tempArr) ;
        }

        arrs = test_mergeSort(arrs, comparator, index+1);

        return arrs ;
    }

    public static void mergeSort_temp(int[] a,int s, int len){
        int size = a.length ;
        int mid = size/(len << 1) ;
        int c = size & ((len << 1) - 1) ;

        /** 归并到只剩一个有序集合时算法结束 */
        if(mid == 0) return  ;

        for(int i=0;i<mid;i++){
            s = i*2*len ;
            merge(a,s,s+len,(len<<1)+s-1) ;
        }

        /** 将剩下的数和倒数一个有序集合归并 */
        if(c != 0){
            merge(a, size-c-2*len, size-c, size-1) ;
        }

        /** 递归下一轮归并排序 */
        mergeSort_temp(a, 0, 2*len);
    }
}
/*
 * 主要的问题：
 * 1、计算每次比较两个数组的起始和结束位置
 * 2、对数组进行划分，最后还剩余不足一次归并的元素个数时如何处理
 */