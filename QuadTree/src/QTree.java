/**
 * Created by r028367 on 15/05/2017.
 */
public class QTree {

    public static final class Node /* implements Comparable<Node> */ {
        private int x, y, w, h, value;
        private Node n,s,l,o;   // cada node possui 4 subnodes
        private Node parent;    // no pai
        public Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    public Node root;

    private boolean less(int x, int y) {
        return x < y;
    }

    private Node createNode(int x, int y, int value) {
        return new Node(x, y, value);
    }

    public void insert(int x, int y, int value) {
        root = insert(root, x, y, value);
    }

    public Node insert(Node node, int x, int y, int value) {
        // primeiro no
        if(node == null) {
            return createNode(x, y, value);
        }
        else if(less(x, node.x) && less(y, node.y)) {
            node.s = insert(node.s, x, y, value);
        }
        else if(less(x, node.x) && !less(y, node.y)) {
            node.n = insert(node.n, x, y, value);
        }
        else if(!less(x, node.x) && less(y, node.y)) {
            node.l = insert(node.l, x, y, value);
        }
        else if(!less(x, node.x) && !less(y, node.y)) {
            node.o = insert(node.o, x, y, value);
        }
        return node;
    }

    private void query(Node node) {

    }

    public static void main(String[] args) {

    }



}
