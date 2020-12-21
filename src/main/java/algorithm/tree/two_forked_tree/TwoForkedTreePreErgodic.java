package algorithm.tree.two_forked_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树先序遍历
 * 先序遍历规则：【根左右】
 * Created by Niki on 2018/5/15 8:47
 */
public class TwoForkedTreePreErgodic {
    public static void main(String[] args) {
        Node root = Node.createTree();
        List<Node> nodes = new ArrayList<>();
        preErgodic(root, nodes);
        nodes.stream().forEach(System.out::println);
    }

    /**
     * 递归的方式实现前序遍历
     * @param root
     */
    private static void preErgodic(Node root, List<Node> nodes) {
        if (root == null) {
            return ;
        }
        nodes.add(root);
        preErgodic(root.getLeft(), nodes);
        preErgodic(root.getRight(),nodes);
    }

    /**
     * 先序遍历解决方案
     */
    class Solution {
        public List<Integer> preTraversal(Node root) {
            List<Integer> res = new ArrayList();
            if (root == null) {
                return res;
            }
            Node p = root;
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || p != null) {

            }
            return res;
        }
    }
}
