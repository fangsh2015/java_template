package algorithm.tree.two_forked_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * 二叉树的中序遍历算法
 * 中序遍历：【左根右】
 * Created by Niki on 2018/5/10 16:33
 */
public class TwoForkedTreeMiddleErgodic {
    public static void main(String[] args) {
        Node root = Node.createTree();
//        middleErgodic(root);
        System.out.println(morris(root));
    }



    /**
     * 中序遍历_递归方式
     * 中序遍历的步骤：左节点，当前节点，右节点
     *
     * @param root
     */
    private static void middleErgodic(Node root) {
        List<Integer> container = new ArrayList<>();
        middleErgodic_(root, container);
        System.out.println(container);
    }

    /**
     * 递归实现中序遍历
     * 实现复杂度O(n);空间复杂度为O(n)
     * @param node
     * @param container
     * @return
     */
    private static List<Integer> middleErgodic(Node node, List<Integer> container) {
        if (node == null) {
            return container;
        } else if (node.getLeft() == null && node.getRight() == null) {
            container.add(node.getValue());
            return container;
        } else if (node.getLeft() == null) {
            //一定有右节点
            container.add(node.getValue());
            container.add(node.getRight().getValue());
            return container;
        } else if (node.getRight() == null) {
            //一定有左节点
            container.add(node.getLeft().getValue());
            container.add(node.getValue());
            return container;
        } else if (node.getLeft().getLeft() == null && node.getRight().getRight() == null) {
            container.add(node.getLeft().getValue());
            container.add(node.getValue());
            container.add(node.getRight().getValue());
            return container;
        }
        middleErgodic(node.getLeft(), container);
        container.add(node.getValue());
        middleErgodic(node.getRight(), container);
        return container;
    }

    private static List<Integer> middleErgodic_(Node node, List<Integer> container) {
        if (node == null) {
            return container;
        }
        middleErgodic_(node.getLeft(), container);
        container.add(node.getValue());
        middleErgodic_(node.getRight(), container);
        return container;
    }

    private static void middleIteration(Node root) {

    }

    /**
     * morris发明的中序遍历算法，时间复杂度O(n),空间复杂度为O(1)
     * @param root
     * @return
     */
    private static Vector<Integer> morris(Node root) {
        Vector<Integer> container = new Vector<>();
        if (root == null) {
            return container;
        }
        Node curr = root;
        Node pre ;
        while (curr != null) {
            if (curr.getLeft() == null) {
                container.add(curr.getValue());
                curr = curr.getRight();
            } else {
                pre = curr.getLeft();
                while (pre.getRight() != null && pre.getRight() != curr) {
                    pre = pre.getRight();
                }
                if (pre.getRight() == null) {
                    pre.setRight(curr);
                    curr = curr.getLeft();
                } else {
                    container.add(curr.getValue());
                    pre.setRight(null);
                    curr = curr.getRight();
                }
            }
        }
        return container;
    }


    class Solution {
        public List<Integer> inorderTraversal(Node root) {
            List<Integer> res = new ArrayList();
            if (root == null) {
                return res;
            }
            Node p = root;
            Stack<Node> stack = new Stack<>();

            while (!stack.isEmpty() || p != null) {
                if (p != null) {
                    stack.push(p);
                    p = p.getLeft();
                }else {
                    p = stack.pop();
                    res.add(p.getValue());
                    p = p.getRight();
                }
            }
            return res;
        }
    }
}
