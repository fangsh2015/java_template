package algorithm.lookup;

import java.util.ArrayList;
import java.util.List;

/**
 * 连个有序的数组，求交集
 * a=[0,1,2,3,4]
 * b = [1,3,5,7,9]
 * Created by Niki on 2018/5/3 13:53
 */
public class Sort_Intersection {
    private int[] a = {0, 1, 2, 3, 4};
    private int[] b = {1, 3, 5, 7, 9};

    private static List<Integer> sort_Intersection(int[] a, int[] b) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] > b[j]) {
                j++;
            } else if (a[i] < b[j]) {
                i++;
            } else {
                result.add(a[i]);
                i++;
                j++;
            }
        }
        return result;
    }

    /**
     * 因为数组是有序的，则可以取一个数组中的值，去另外一个数组中进行比较。
     * 比较的方式采用折半查找的方式（因为有序的缘故）
     * 并且记录下下标，下次比较从上次下标处开始。
     *
     * @param a
     * @param b
     * @return
     */
    private static List<Integer> sort_Intersection_2(int[] a, int[] b) {
        List<Integer> result = new ArrayList<>();
        int index = a.length;
        for (int i = b.length - 1; i >= 0; i--) {
            index = binarySearch(a, index, b[i]);
            if (a[index] == b[i]) {
                result.add(a[index]);
            }
            if (index < 1) {
                return result;
            }
        }
        return result;
    }

    /**
     * 折半查找
     *
     * @param a
     * @param size
     * @param target
     * @return
     */
    private static int binarySearch(int[] a, int size, int target) {
        if (size < 1) {
            return -1;
        }
        int l = 0, r = size - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (a[m] == target) {
                return m;
            } else if (a[m] > target) {
                r = m - 1;
            } else {
                l = m - 1;
            }
        }
        return 1 ;
    }
}

