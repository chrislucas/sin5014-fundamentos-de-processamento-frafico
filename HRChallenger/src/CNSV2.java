import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by r028367 on 13/07/2017.
 * https://www.hackerrank.com/challenges/a-circle-and-a-square
 */
public class CNSV2 {

    static int [][] posRoration = {
        {(int) cos(toRadians(90)), (int) -sin(toRadians(90))}
        ,{(int) sin(toRadians(90)), (int)  cos(toRadians(90))}
    };

    static int [][] negRotation = {
        {(int)  cos(toRadians(90)), (int) sin(toRadians(90))}
        ,{(int) -sin(toRadians(90)), (int) cos(toRadians(90))}
    };

    static class Point2D {
        int x, y;
        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Circle {
        int centerX, centerY, radius;
        public Circle(int centerX, int centerY, int radius) {
            this.centerX    = centerX;
            this.centerY    = centerY;
            this.radius     = radius;
        }
    }

    static class Rect {
        /*
        * Pontos na diagonal
        * */
        Point2D pb, pd;

        public Rect(Point2D pb, Point2D pd) {
            this.pb = pb;
            this.pd = pd;
        }

        public Point2D getCenter() {
            return null;
        }
    }

}


