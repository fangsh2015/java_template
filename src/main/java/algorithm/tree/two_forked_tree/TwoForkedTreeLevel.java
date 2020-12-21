package algorithm.tree.two_forked_tree;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树层序遍历
 * 输出二叉树每一行的所有值
 * Created by Niki on 2020/10/9 14:29
 */
public class TwoForkedTreeLevel {
    public static void main(String[] args) {
        final Node tree = Node.createTree_();
        final List<List<Integer>> lists = levelOrder(tree);
        final List<List<Integer>> lists2 = levelOrder2(tree);
        System.out.println(lists);
        System.out.println(lists2);
        final List<List<Integer>> lists3 = levelOrder3(tree);
        System.out.println(lists3);
    }

    /**
     * 广度优先遍历方式
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        int currLevCnt = 1;
        int nextLevCnt = 0;

        List<Integer> levelres = Lists.newArrayList();

        while (!queue.isEmpty()) {
            final Node curr = queue.poll();
            currLevCnt--;
            levelres.add(curr.getValue());

            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
                nextLevCnt++;
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
                nextLevCnt++;
            }

            if (currLevCnt == 0) {
                currLevCnt = nextLevCnt;
                nextLevCnt = 0;
                res.add(levelres);
                levelres = Lists.newArrayList();
            }
        }
        return res;
    }

    /**
     * 广度优先遍历
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder2(Node root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelRes = Lists.newArrayList();
            for (int i = 0; i < levelSize; i++) {
                final Node p = queue.poll();
                levelRes.add(p.getValue());

                if (p.getLeft() != null) {
                    queue.add(p.getLeft());
                }

                if (p.getRight() != null) {
                    queue.add(p.getRight());
                }
            }
            res.add(levelRes);
        }
        return res;
    }

    /**
     * 深度优先算法
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder3(Node root) {
        List<List<Integer>> res = Lists.newArrayList();
        // 递归的层级
        int level = 0;

        return dfs(root, level, res);

    }

    private static List<List<Integer>> dfs(Node root, int level, List<List<Integer>> res) {
        if (root == null) {
            return res;
        }

        List<Integer> currentList;

        if (level == res.size()) {
            currentList = Lists.newArrayList();
            currentList.add(root.getValue());
            res.add(currentList);
        } else {
            currentList = res.get(level);
            currentList.add(root.getValue());
            res.set(level, currentList);
        }
        dfs(root.getLeft(), level + 1, res);
        dfs(root.getRight(), level + 1, res);

        return res;
    }
}
