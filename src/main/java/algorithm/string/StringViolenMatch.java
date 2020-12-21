package algorithm.string;

/**
 * 字符串包里匹配算法
 * 逐个字符匹配，来判断source字符串是否包含target字符串
 * https://blog.csdn.net/v_july_v/article/details/7041827
 * Created by Niki on 2020/11/5 16:42
 */
public class StringViolenMatch {
    public int violenMatch(String source, String target) {
        int sLen = source.length();
        int tLen = source.length();
        final char[] sCharrArr = source.toCharArray();
        final char[] tCharrArr = target.toCharArray();
        int i=0, j=0;
        while (i < sLen && j < tLen) {
            if (sCharrArr[i] == tCharrArr[j]) {
                i++;
                j++;
            }else{
                i = i - (j - 1);
                j = 0;
            }
        }
        if (j == sLen) {
            return i - j ;
        }
        return -1;
    }
}

