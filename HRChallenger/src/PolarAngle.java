import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 28/06/2017.
 */

import static java.lang.Math.*;

public class PolarAngle {

    public static class Point2f {
        public static final double EPS = 0.000000001;
        double x, y, theta;
        public Point2f(double x, double y, double theta) {
            this.x     = x;
            this.y     = y;
            this.theta = theta;
        }

        //(0,0)
        public double distanceOfOrigin(Point2f p) {
            return Math.sqrt(p.x*p.x+p.y*p.y);
        }

        public boolean almostEquals(double a, double b) {
            if( abs(a-b) < EPS)
                return true;
            return false;
        }
        public Comparator<Point2f> fn = new Comparator<Point2f>() {
            @Override
            public int compare(Point2f pa, Point2f pb) {
                if(almostEquals(pa.theta, pb.theta)) {
                    double da = distanceOfOrigin(pa);
                    double db = distanceOfOrigin(pb);
                    if(almostEquals(da, db)) {
                        return 0;
                    }
                    return (int) abs(da - db);
                }
                return (int) abs(pa.theta - pb.theta);
            }
        };
    }

    public static class Point2D {
        double x, y;
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static Point2f toPolar(double x, double y) {
        double theta = toDegrees(atan2(y,x));   // *180/PI
        Point2f p = new Point2f(x, y, theta);
        return null;
    }

    public static Point2D toRect(Point2f p) {
        double x    = p.x, y = p.y;
        double r    = Math.sqrt(x*x+y*y);
        double rcos = toRadians(cos(p.theta));
        double rsin = toRadians(sin(p.theta));
        Point2D point2D  = new Point2D(r*rcos,  r*rsin);
        return point2D;
    }

    static ArrayList<Point2f> points;

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            points = new ArrayList<>();
            for(;cases>0;cases--) {
                StringTokenizer tk = new StringTokenizer(bufferedReader.readLine(), " ");
                double x = Double.parseDouble(tk.nextToken());
                double y = Double.parseDouble(tk.nextToken());
                Point2f p = toPolar(x, y);
                points.add(p);
            }
        } catch (IOException ioex) {

        }
    }


    public static void main(String[] args) {
        System.out.println(toDegrees(atan2(5.0, 12.0)));
    }


}
