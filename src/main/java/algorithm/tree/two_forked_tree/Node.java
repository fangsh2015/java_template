package algorithm.tree.two_forked_tree;

/**
 * 二叉树的节点对象
 * Created by Niki on 2018/5/10 16:34
 */
public class Node {
    private Node left;
    private Node right;
    private Integer value;

    public Node() {
    }

    public Node(Integer value) {
        this.value = value;
    }

    public Node(Node left, Node right, Integer value) {

        this.left = left;
        this.right = right;
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    protected static Node createTree() {
        Node root = new Node(10);
        Node l1 = new Node(5);
        Node r1 = new Node(15);
        Node r1l1 = new Node(6);
        Node r1r1 = new Node(2);
        root.setLeft(l1);
        root.setRight(r1);
        r1.setLeft(r1l1);
        r1.setRight(r1r1);

        Node l1l1 = new Node(3);
        Node l1r1 = new Node(4);
        l1.setLeft(l1l1);
        l1.setRight(l1r1);

        return root;
        //中序：[3, 5, 4, 10, 6, 15, 2]
        //先序：[10,5,3,4,15,6,2]
        //后序：[3, 4, 5, 6, 2, 15, 10]
    }

    protected static Node createTree_(){
        Node root = new Node(1);
        Node l_1 = new Node(2);
        Node lR_1 = new Node(3);
        Node lrl_1 = new Node(4);
        root.setLeft(l_1);
        l_1.setRight(lR_1);
        lR_1.setLeft(lrl_1);


        Node r = new Node(5);
        Node rr = new Node(6);
        Node rrl = new Node(7);
        Node rrll = new Node(8);
        Node rrlr = new Node(9);
        rrl.setLeft(rrll);
        rrl.setRight(rrlr);

        rr.setLeft(rrl);
        r.setRight(rr);
        root.setRight(r);
        return root;
    }

    /**
     * 创建一棵查找二叉树
     * @return
     */
    protected static Node createBSTree() {
        Node root = new Node(4);
        Node root2 = new Node(2);
        Node root1 = new Node(1);
        Node root3 = new Node(3);
        Node root5 = new Node(5);
        Node root6 = new Node(6);
        Node root7 = new Node(7);

        root2.setLeft(root1);
        root2.setRight(root3);
        root.setLeft(root2);

        root6.setLeft(root5);
        root6.setRight(root7);
        root.setRight(root6);

        return root;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
