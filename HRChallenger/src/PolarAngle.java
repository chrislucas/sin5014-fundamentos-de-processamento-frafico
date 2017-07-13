import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 28/06/2017.
 */

import static java.lang.Math.*;

public class PolarAngle {

    public static class Point2f {
        double r, x, y, theta;
        public Point2f(double r, double x, double y, double theta) {
            this.r      = r;
            this.x      = x;
            this.y      = y;
            this.theta  = theta;
        }
        @Override
        public String toString() {
            return String.format("%f %f", this.r, this.theta);
        }
    }
    public static final double EPS = 0.000000001;

    public static boolean almostEquals(double a, double b) {
        if( abs(a-b) < EPS)
            return true;
        return false;
    }

    public static Comparator<Point2f> fn = new Comparator<Point2f>() {
        @Override
        public int compare(Point2f pa, Point2f pb) {
            if(almostEquals(pa.theta, pb.theta)) {
                if(almostEquals(pa.r, pb.r)) {
                    return 0;
                }
                double diff = (pa.r - pb.r);
                if(almostEquals(diff, 0))
                    return 0;
                return diff < 0 ? -1 : 1;
            }
            double diff = (pa.theta - pb.theta);
            if(almostEquals(diff, 0))
                return 0;
            return diff < 0 ? -1 : 1;
        }
    };

    public static class Point2D {
        double x, y;
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static Point2f toPolar(double x, double y) {
        double theta = toDegrees(atan2(y,x));   // *180/PI
        theta        =  theta < 0 ? 360 + theta : theta;
        double r     = sqrt(x*x+y*y);
        Point2f p    = new Point2f(r, x, y, theta);
        return p;
    }

    public static Point2D toRect(Point2f p) {
        double r    = p.r;
        double rcos = toRadians(cos(p.theta));
        double rsin = toRadians(sin(p.theta));
        Point2D point2D  = new Point2D(r*rcos,  r*rsin);
        return point2D;
    }

    static ArrayList<Point2f> points;

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine().trim());
            points = new ArrayList<>();
            for(;cases>0;cases--) {
                StringTokenizer tk = new StringTokenizer(bufferedReader.readLine(), " ");
                double x = Double.parseDouble(tk.nextToken());
                double y = Double.parseDouble(tk.nextToken());
                Point2f p = toPolar(x, y);
                points.add(p);
            }
            Collections.sort(points, fn);
            for(Point2f p : points) {
                //System.out.printf("%d %d %f %f\n", (int)p.x, (int)p.y, p.theta, p.r);
                System.out.printf("%d %d\n", (int)p.x, (int)p.y);
            }
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        solver();
    }
}
