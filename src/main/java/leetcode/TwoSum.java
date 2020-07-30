package leetcode;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Niki on 2020/7/15 16:45
 */
public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        int i = 0;
        int j = 0;
        int[] test = new int[2];
        for (i = 0; i < nums.length; i++) {
            for (j = i + j; j < nums.length; j++) {
                int temp = nums[i] + nums[j];
                if (temp == target) {
                    test[0] = i;
                    test[1] = j;
                    break;
                }
            }
        }
        return test;
    }

    public static int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            hash.put(nums[i], i);
        }
        int nextKey = 0;
        for (int key : hash.keySet()) {
            nextKey = target - key;
            if (hash.containsKey(nextKey)) {
                return new int[]{hash.get(key), hash.get(nextKey)};
            }
        }
        return null;
    }


    public static void main(String[] args) {
        int[] numArr = {1, 2, 5, 9};
        final int[] ints = twoSum2(numArr, 10);
        System.out.println(ints);

        final String s = Integer.toString(123);
        final char[] chars = s.toCharArray();

    }

}
