package leetcode.tree.binary_tree;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

/**
 * 二叉树中序遍历
 * 左，根， 右
 * Created by Niki on 2020/7/21 9:34
 */
public class InOrderTraversal {

    /**
     * @param root
     * @return
     */
    public List<Integer> InOrderTraversal(TreeNode root) {
        List<Integer> res = Lists.newArrayList();
        Stack<TreeNode> treeNodes = new Stack<>();
        treeNodes.add(root);
        TreeNode curr = root;
        while (!treeNodes.isEmpty()) {

            treeNodes.push(root.right);
            treeNodes.push(root.left);

        }
        return null;
    }

    public List<Integer> inorderTraversalByStacck(TreeNode root) {
        List<Integer> res = Lists.newArrayList();
        if (root == null) {
            return res;
        }
        TreeNode p = root;
        Stack<TreeNode> stack = new Stack<>();

        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            }else {
                p = stack.pop();
                res.add(p.val);
                p = p.right;
            }
        }
        return res;
    }


}
