package algorithm.tree.two_forked_tree;

/**
 * 红黑树：红黑树也是二叉树；红黑树的特性
 * 1、根节点一定是黑色，并且节点不是黑色就是红色
 * 2、叶子节点（为空）一定是黑色
 * 3、红节点的子节点一定都是黑色的
 * 4、从一个节点到该节点的子孙节点的所有的路径上包含相同数据的黑节点
 * Created by Niki on 2018/5/16 20:35
 */
public class BRTree<T extends Comparable<T>> {

    private class BRTNode<T> {
        boolean color;
        T key;
        BRTNode<T> left;
        BRTNode<T> right;
        BRTNode<T> parent;

        public BRTNode(boolean color, T key, BRTNode<T> left, BRTNode<T> right, BRTNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public BRTNode<T> getLeft() {
            return left;
        }

        public void setLeft(BRTNode<T> left) {
            this.left = left;
        }

        public BRTNode<T> getRight() {
            return right;
        }

        public void setRight(BRTNode<T> right) {
            this.right = right;
        }

        public BRTNode<T> getParent() {
            return parent;
        }

        public void setParent(BRTNode<T> parent) {
            this.parent = parent;
        }
    }

    /*
     * 对红黑树的节点(x)进行左旋转
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)-.           / \                #
     *  lx   y                          x  ry
     *     /   \                       /  \
     *    ly   ry                     lx  ly
     *
     *
     */
    private void leftRotate(BRTNode<T> node) {
        BRTNode right = node.getRight();
        node.setRight(right.getLeft());
        if (right.getLeft() != null) {
            right.getLeft().setParent(node);
        }

        right.setParent(node.getParent());

        if (node.getParent() == null) {
            //节点是父节点，则将y节点设为根节点
//            right.setParent(null);
        } else {
            //将x的父节点设为y的父节点
            if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(right);
            } else if (node.getParent().getRight() == node) {
                node.getParent().setRight(right);
            }
        }

        right.setLeft(node);
        node.setParent(right);
    }

    /*
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)-.            /  \                     #
     *        x   ry                           lx   y
     *       / \                                   / \                   #
     *      lx  rx                                rx  ry
     *
     */
    private void rightRotate(BRTNode<T> y) {
        //右旋获得其左节点
        BRTNode x = y.getLeft();
        //右旋节点，其左子节点，的右子节点xr
        BRTNode xr = x.getRight();
        //将y节点的左节点设为被旋节点的右节点xr
        y.setLeft(xr);
        //如果xr不为空，则将xr的父节点设置为y节点
        if (xr != null) {
            xr.setParent(y);
        }

        //将y的父节点设置为旋转节点x的父节点（X节点代替Y节点的位置）
        BRTNode parent = y.getParent();
        x.setParent(parent);
        if (parent == null) {
            //x设置为根节点
        } else {
            //将新的被旋上去的节点设置为父节点对应的左或右子节点
            if (parent.getLeft() == y) {
                parent.setLeft(x);
            } else if (parent.getRight() == y) {
                parent.setRight(x);
            }
        }
        //将被旋节点y设置为旋上节点的右子节点
        x.setRight(y);
        y.setParent(x);
    }

}
