package leetcode.tree.binary_tree;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

/**
 * 二叉树先序遍历方案
 * 根，左，右
 * Created by Niki on 2020/7/20 9:44
 */
public class PreorderTraversal {

    /**
     * 利用栈先进后出的原理实现二叉树的先序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversalByStack(TreeNode root) {
        List<Integer> res = Lists.newArrayList();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> treeNodes = new Stack<>();
        treeNodes.push(root);

        while (!treeNodes.isEmpty()) {
            TreeNode cur = treeNodes.pop();
            res.add(cur.val);
            if (cur.right != null) {
                treeNodes.push(cur.right);
            }
            if (cur.left != null) {
                treeNodes.push(cur.left);
            }

        }
        return res;
    }

    public List<Integer> preorderTraversalByStack2(TreeNode root) {
        List<Integer> res = Lists.newArrayList();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> treeNodes = new Stack<>();
        TreeNode p = root;
        while (!treeNodes.isEmpty() || p != null) {
            p = treeNodes.pop();
            if (p != null) {
                res.add(p.val);
                treeNodes.push(p.left);
            }else{

            }
        }
        return null;
    }

    /**
     * 利用递归的方式，实现二叉树的先序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversalByRecursion(TreeNode root, List<Integer> res) {
        if (root == null) {
            return res;
        }
        res.add(root.val);
        preorderTraversalByRecursion(root.left, res);
        preorderTraversalByRecursion(root.right, res);
        return res;
    }


    public static void main(String[] args) {
    }
}
