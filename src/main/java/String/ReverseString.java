package String;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 饭庄字符串
 * Created by Niki on 2018/11/26 9:10
 */
public class ReverseString {
    /**
     * 首尾交换字符串
     *
     * @param string
     * @return
     */
    public static String reverseWithSwaps(String string) {
        final char[] array = string.toCharArray();
        final int length = array.length;
        final int half = (int) Math.floor(array.length / 2);

        char c;
        for (int i = 0; i < half; i++) {
            c = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = c;
        }
        show(string, String.valueOf(array));
        return String.valueOf(array);
    }

    /**
     * 使用xor交换首尾字符串
     *
     * @param string
     * @return
     */
    public static String reverseWithXOR(String string) {
        final char[] array = string.toCharArray();
        final int length = array.length;

        final int half = (int) Math.floor(array.length / 2);
        for (int i = 0; i < half; i++) {
            int test1 = array[i];
            int test2 = array[length - i - 1];
            int temp = array[i] ^ array[length - i - 1];
            array[i] = (char) temp;
            array[length - i - 1] ^= array[i];
            array[i] ^= array[length - i - 1];
        }
        show(string, String.valueOf(array));
        return String.valueOf(array);
    }

    public static String reverseWithStack(String string) {
        Stack<Character> stack = new Stack<>();
        char[] array = string.toCharArray();
        for (char c : array) {
            stack.push(c);
        }
        int k = 0;
        while (!stack.isEmpty()) {
            array[k++] = stack.pop();
        }
        show(string, String.valueOf(array));
        return String.valueOf(array);
    }


    public static String reverseWithCollectionUtil(String input) {
        char[] array = input.toCharArray();
        List<Character> list = new ArrayList<>();
        for (char c : array) {
            list.add(c);
        }

        Collections.reverse(list);

        String output = list.stream().map(character -> character.toString()).collect(Collectors.joining(""));
        show(input, output);
        return output;
    }

    /**
     * 递归加上substring方法
     *
     * @param input
     * @return
     */
    public static String reverseWithSubstring(String input) {
        return input.charAt(input.length() - 1) + reverseWithSwaps(input.substring(0, input.length() - 1));
    }


    private static void show(String input, String output) {
        System.out.println("input string is:" + input);
        System.out.println("output string is:" + output);
    }

    public static void main(String[] args) {
        reverseWithXOR("niki");
    }
}
