package algorithm.sort;

/**
 * 快速排序
 * @author 80002165 @date 2017年4月28日 上午9:34:39
 */
/**
 * 算法思路：
 * 主要采用递归，挖坑的方式来实现算法的思路
 * 选取数组中一个数，作为关键数A，设置i,j从数组头和数组尾遍历数组，如果a[i]比A大则换位置，如果a[j]比A小换位置（从小到大排序）
 * 一般关键数从数组第一个数开始
 */
/**
 * 算法演示：
 * 原数组   3 2 7 6 8 9
 * 第一趟   2 3 7 6 8 9
 * 第二趟   2 3 6 7 8 9
 */
/**
 * 算法性能：
 */
/**
 * 时间复杂度：O(N*logN)
 * 最坏情况：
 * 最好情况：
 */
/**
 * 算法稳定性： 该算法不稳定
 */
public class QuickSort {
    private static int[] swap(int[] arrs) {
        int len = arrs.length ;
        int indexNum = arrs[0] ;// 每次都是从0开始
        int i = 0, j = len - 1 ;
        while (true) {
            if (i == (j + 1))
                break ;
            // 先从后往前找，找比indexNum小与i互换位置

            // int tempJ = arrs[j--] ;
            // if (tempJ < indexNum) {
            // arrs[i] = tempJ ;
            // }
            while (true) {
                if (i == j) {
                    arrs[i] = indexNum ;
                    break ;
                }
                int tempJ = arrs[j--] ;
                if (tempJ < indexNum) {
                    arrs[i] = tempJ ;
                    break ;
                }
            }

            // 从前往后找，比indexNum小的与j互换位置
            // int tempI = arrs[++i] ;
            // if (tempI < indexNum) {
            // arrs[j] = tempI ;
            // }
            while (true) {
                if (j + 1 == i) {
                    arrs[i] = indexNum ;
                    break ;
                }
                int tempI = arrs[++i] ;
                if (tempI > indexNum) {
                    arrs[j + 1] = tempI ;
                    break ;
                }
            }

        }
        return arrs ;
    }

    /**
     *
     * @author 80002165 @date 2017年5月3日 上午10:32:22
     * @param arrs
     * @param begin
     *        开始下标
     * @param end
     *        结束下标
     */
    private static void swap(int[] arrs, int begin, int end) {
        int i = begin, j = end ;
        int indexNum = arrs[begin] ;// 选取关键数，默认都是0
        if ((end - begin) < 1)
            return ;
        while (true) {
            if (i == j) {
                arrs[i] = indexNum ;
                swap(arrs, begin, i - 1) ;
                swap(arrs, i + 1, end) ;
            }
            while (true) {
                if (j == i) {
                    arrs[i] = indexNum ;
                    swap(arrs, begin, i - 1) ;
                    swap(arrs, i + 1, end) ;
                    // return ;
                }
                if (arrs[j] < indexNum) {
                    arrs[i] = arrs[j] ;
                    break ;
                }
                j-- ;
            }

            while (true) {
                if (j == i) {
                    arrs[i] = indexNum ;
                    swap(arrs, begin, i - 1) ;
                    swap(arrs, i + 1, end) ;
                    // return ;
                }
                if (arrs[i + 1] > indexNum) {
                    arrs[j] = arrs[i + 1] ;
                    i++ ;
                    break ;
                }
                i++ ;
            }
        }
    }

    private static void divideAndConquer(int[] arrs) {

    }

    static int adjustArray(int s[], int l, int r) { // 返回调整后基准数的位置
        int i = l, j = r ;
        int x = s[l] ; // s[l]即s[i]就是第一个坑
        while (i < j) {
            // 从右向左找小于x的数来填s[i]
            while (i < j && s[j] >= x) {
                j-- ;
            }
            if (i < j) {
                s[i] = s[j] ; // 将s[j]填到s[i]中，s[j]就形成了一个新的坑
                i++ ;
            }

            // 从左向右找大于或等于x的数来填s[j]
            while (i < j && s[i] < x) {
                i++ ;
            }
            if (i < j) {
                s[j] = s[i] ; // 将s[i]填到s[j]中，s[i]就形成了一个新的坑
                j-- ;
            }
        }
        // 退出时，i等于j。将x填到这个坑中。
        s[i] = x ;

        return i ;
    }

    public static void sort(int[] arrs, int begin, int end) {
        int index = adjustArray(arrs, begin, end) ;
        if (index > 1) {
            sort(arrs, begin, index - 1) ;
        }
        if (index < end) {
            sort(arrs, index + 1, end) ;
        }
    }

    public static void quickSort(int[] arrs, int begin, int end){
        if(begin < end){
            int index = adjustArray(arrs, begin, end) ;
            quickSort(arrs, begin, index-1);
            quickSort(arrs, index+1, end);
        }
    }

    public static void main(String[] args) {
        int[] arrs = { 72, 6, 57, 88, 60, 42, 83, 73, 48, 85 } ;
        // adjustArray(arrs, 0, arrs.length - 1) ;
//        sort(arrs, 0, arrs.length - 1) ;
        quickSort(arrs, 0, arrs .length-1);
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]) ;
        }
    }
}
