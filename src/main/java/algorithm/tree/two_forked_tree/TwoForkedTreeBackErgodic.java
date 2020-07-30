package algorithm.tree.two_forked_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树后序遍历
 * 后序遍历规则：【左右根】
 * Created by Niki on 2018/5/15 8:48
 */
public class TwoForkedTreeBackErgodic {
    public static void main(String[] args) {
        Node root = Node.createTree();
        List<Integer> nodes = new ArrayList<>();
        backErgodic(root, nodes);
        nodes.stream().forEach(System.out::println);
        System.out.println(nodes);
    }

    /**
     * 使用递归的防守实现的后序遍历算法
     *
     * @param root
     */
    private static void backErgodic(Node root, List<Integer> nodes) {
        if (root == null) {
            return;
        }
        backErgodic(root.getLeft(), nodes);
        backErgodic(root.getRight(), nodes);
        nodes.add(root.getValue());
    }
}
