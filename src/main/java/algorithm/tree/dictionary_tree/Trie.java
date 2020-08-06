package algorithm.tree.dictionary_tree;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringUtils;

/**
 * 字典树
 * Created by Niki on 2020/8/6 11:20
 */
public class Trie {
    /**
     * 各个节点的子树数目即字符串中的字符出现的最多种类
     */
    private final int SIZE = 26;

    /**
     * 除根节点外其他所有子节点的数目
     */
    private int numNode;

    /**
     * 树的深度即最长字符串的长度
     */
    private int depth;

    /**
     * 字典树的根
     */
    private TrieNode root;


    private class TrieNode {
        /**
         * 所有子节点，或者一级自及诶单
         */
        private TrieNode[] son;

        /**
         * 有多少字符串经过或到达这个自及诶单，及节点字符出现的次数
         */
        private int numPass;

        /**
         * 有多少字符串通过这个节点并到此结束的数量
         */
        private int numEnd;

        /**
         * 是否有结束节点
         */
        private boolean isEnd;

        /**
         * 节点的值
         */
        private char value;

        /**
         * 初始化节点类
         */
        public TrieNode() {
            this.numPass = 0;
            this.numEnd = 0;
            this.son = new TrieNode[SIZE];
            this.isEnd = false;
        }
    }

    /**
     * 判断字符串是否为空，是否为字母构成的字符串
     *
     * @param str
     * @return
     */
    private boolean isStrOfLetter(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        // 判断字符串是否为字符组成
        if (!CharMatcher.JAVA_LETTER.matchesAllOf(str)) {
            return false;
        }
        return true;
    }

    private char[] toCharArr(String str){
        str = str.toLowerCase();
        return str.toCharArray();

    }

    /**
     * 插入字符创
     * @param str 需要插入的字符串
     * @return 是否插入成功
     */
    public boolean insertStr(String str) {
        if (!isStrOfLetter(str)) {
            return false;
        }
        // 不区分大小写

        char[] letters = toCharArr(str);
        TrieNode node = this.root;
        for (char c : letters) {
            // 计算当前字符所在的位置
            int pos = c - 'a';
            if (node.son[pos] == null) {
                node.son[pos] = new TrieNode();
                node.son[pos].value = c;
                node.son[pos].numPass = 1;
                this.numNode++;
            } else {
                node.son[pos].numPass++;
            }
            node = node.son[pos];
        }
        node.isEnd = true;
        node.numEnd++;
        if (letters.length > this.depth) {
            this.depth = letters.length;
        }
        return true;
    }

    /**
     * 查找是否有某前缀开头的字符串方法
     * @param str
     * @return
     */
    public boolean isContainPrefix(String str) {
        if (!isStrOfLetter(str)) {
            return false;
        }

        char[] letters = toCharArr(str);
        TrieNode node = this.root;
        for (char c : letters) {
            int pos = c - 'a';
            if (node.son[pos] != null) {
                node = node.son[pos];
            }else{
                // 某一个字符不符合则中断循环
                return false;
            }
        }
        return true;
    }

    /**
     * 统计以指定字符串为前缀的字符串数量，不区分大小写
     * @param str
     * @return
     */
    public int countPrefix(String str) {
        if (!isStrOfLetter(str)) {
            return 0;
        }

        final char[] letters = toCharArr(str);
        TrieNode node = this.root;
        for (char c : letters) {
            int pos = c - 'a';
            if (node.son[pos] == null) {
                return 0;
            } else {
                node = node.son[pos];
            }
        }
        return node.numPass;
    }
}
