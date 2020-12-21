package algorithm.tree.two_forked_tree;

/**
 * 判断二叉树是否为对称二叉树
 * Created by Niki on 2020/10/10 12:51
 */
public class SymmetricTree {

    public static boolean isSymmetric(Node node) {
        return false;
    }

    private static boolean isMirror(Node n1, Node n2) {
        if (n1 == null && n2 == null) {
            return true;
        }

        if (n1 == null || n2 == null) {
            return false;
        }

        return (n1.getValue().equals(n2.getValue())) &&
                isMirror(n1.getLeft(), n2.getRight()) &&
                isMirror(n1.getRight(), n2.getLeft());

    }
}
