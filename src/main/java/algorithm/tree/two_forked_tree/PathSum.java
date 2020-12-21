package algorithm.tree.two_forked_tree;

/**
 * https://leetcode.com/explore/learn/card/data-structure-tree/17/solve-problems-recursively/537/
 * 判断一颗树上是否存在一条路径中所有节点的值之和与给定的sum值相同
 * Created by Niki on 2020/10/15 9:05
 */
public class PathSum {

    /**
     * 判断是否有一条路径之和等于sum
     * 使用广度优先的算法进行遍历
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(Node root, int sum) {
        if (root == null) {
            return false;
        }
        sum -= root.getValue();
        if (sum == 0 && root.getLeft() == null && root.getRight() == null) {
            return true;
        }
        return hasPathSum(root.getLeft(), sum) || hasPathSum(root.getRight(), sum);
    }
}
