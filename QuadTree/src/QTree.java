/**
 * Created by r028367 on 15/05/2017.
 */
public class QTree {

    //
    public static class Point2D {
        int x, y;
        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Rect {
        Point2D p1, p2;
        public Rect(Point2D p1, Point2D p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public int minX() {
            return p1.x < p2.x ? p1.x : p2.x;
        }

        public int minY() {
            return p1.y < p2.y ? p1.y : p2.y;
        }

        public int maxX() {
            return p1.x < p2.x ? p2.x : p1.x;
        }

        public int maxY() {
            return p1.y < p2.y ? p2.y : p1.y;
        }
    }

    public static final class Node /* implements Comparable<Node> */ {
        private int x, y, w, h, value;
        private Node nw, ne, sw, se;   // cada node possui 4 subnodes
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
            node.sw = insert(node.sw, x, y, value);
        }
        else if(less(x, node.x) && !less(y, node.y)) {
            node.nw = insert(node.nw, x, y, value);
        }
        else if(!less(x, node.x) && less(y, node.y)) {
            node.se = insert(node.se, x, y, value);
        }
        else if(!less(x, node.x) && !less(y, node.y)) {
            node.ne = insert(node.ne, x, y, value);
        }
        return node;
    }

    private void query(Rect rect) {
        query(root, rect);
    }

    private void query(Node node, Rect rect) {
        if(node == null)
            return;
        int minX = rect.minX();
        int minY = rect.minY();
        int maxX = rect.maxX();
        int maxY = rect.maxY();
    }



    public static void main(String[] args) {

    }



}
