package algorithm.tree.two_forked_tree;

/**
 * 查找二叉树的最大深度
 * 根节点的深度为1， 有一个子节点，则树的深度加1
 * Created by Niki on 2020/10/10 9:52
 */
public class MaximumDepth {
    private static int res = 0;

    /**
     * 采用递归的方式计算数的深度 自上而下
     *
     * @param root
     * @return
     */
    public static void topDownMaxDpth(Node root, int depth) {
        if (root == null) {
            return;
        }

        /**
         * 加上这个判断条件，和不加这个判断条件最终得到的二叉树的深度是一样的， 但是如果加上条件，可以减少n-1次最大值的计算
         * 加上这个判断则在最大的叶子节点进行判断，否则是每个节点都会进行判断
         */
        if (root.getLeft() == null && root.getRight() == null) {
            res = Math.max(res, depth);
        }
        topDownMaxDpth(root.getLeft(), depth + 1);
        topDownMaxDpth(root.getRight(), depth + 1);
    }

    /**
     * 自下而上，递归计算二叉树最大深度
     *
     * @param root
     * @return
     */
    public static int bottomUpMaxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        final int leftDepth = bottomUpMaxDepth(root.getLeft());
        final int rightDepth = bottomUpMaxDepth(root.getRight());
        return Math.max(leftDepth, rightDepth) + 1;
    }

    public static void main(String[] args) {
        final Node root = Node.createTree();
        topDownMaxDpth(root, 1);
        System.out.println(res);

        System.out.println(bottomUpMaxDepth(root));
    }
}
