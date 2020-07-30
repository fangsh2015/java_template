package algorithm.tree.two_forked_tree;

import com.sun.tools.internal.ws.wsdl.document.Output;

import java.util.ArrayList;
import java.util.List;

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
//        nodes.stream().forEach(s -> System.out.println(s));
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
}
