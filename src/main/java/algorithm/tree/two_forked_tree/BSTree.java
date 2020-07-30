package algorithm.tree.two_forked_tree;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 查找二叉树
 * Created by Niki on 2018/5/17 9:03
 */
public class BSTree {
    public static void main(String[] args) {
        Node root = Node.createTree();
        System.out.println(judgeBSTree(root));
    }

    /**
     * 判断一棵树是否为查找二叉树
     * 该方法采用中序遍历的方式，因为查找二叉树的中序遍历一定是一个从大到小的有序集。如果符合这一点，就是查找二叉树，否则就不是查找二叉树
     *
     * @param root
     * @return
     */
    public static Boolean judgeBSTree(Node root) {
        //采用一个有序的集合来保存节点中的数字
        LinkedList<Integer> values = new LinkedList<Integer>();
        try {
            midle(root, values);
        } catch (RuntimeException e) {
            return false;
        }
        System.out.println(values);

        return true;
    }

    private static void midle(Node root, LinkedList<Integer> values) {
        if (root == null) {
            return;
        }
        midle(root.getLeft(), values);
        int currValue = root.getValue();
        try {
            int maxValue = values.getLast();
            if (currValue < maxValue) {
                throw new RuntimeException("该树不是查找二叉树");
            }
        } catch (NoSuchElementException e) {

        }
        values.add(currValue);
        midle(root.getRight(), values);
    }
}
