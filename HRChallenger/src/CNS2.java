/**
 * Created by C_Luc on 13/07/2017.
 */
public class CNS2 {

    public static int euclidianDistance(Point2D A, Point2D B) {
        return 0;
    }

    static class Point2D {
        int x, y;

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    static class Circle {

    }


    static class Rect {
        Point2D pa, pc, pb, pd;

        public Rect(Point2D pa, Point2D pc) {
            this.pa = pa;
            this.pc = pc;
        }

        public Rect(Point2D pa, Point2D pc, Point2D pb, Point2D pd) {
            this.pa = pa;
            this.pc = pc;
            this.pb = pb;
            this.pd = pd;
        }

        public Rect complete() {
            Point2D pb = null, pd = null;
            return new Rect(pa, pb, pc, pd);
        }

        public boolean isInside(Point2D p) {
            return false;
        }
    }

}
