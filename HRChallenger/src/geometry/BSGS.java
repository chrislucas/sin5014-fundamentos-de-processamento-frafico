package geometry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 16/06/2017.
 * https://www.hackerrank.com/challenges/baby-step-giant-step
 * https://en.wikipedia.org/wiki/Raster_graphics
 *
 * Point inside rect
 * https://math.stackexchange.com/questions/190111/how-to-check-if-a-point-is-inside-a-rectangle
 *
 * Point inside Rect know opposite diagonal points
 * https://www.quora.com/Given-two-diagonally-opposite-points-of-a-square-how-can-I-find-out-the-other-two-points-in-terms-of-the-coordinates-of-the-known-points
 *
 * Rotation Matrix
 * https://en.wikipedia.org/wiki/Rotation_matrix
 *
 * Online area triangle
 * https://planetcalc.com/218/
 * http://www.mathopenref.com/coordpolygonarea.html
 *
 * https://math.stackexchange.com/questions/516219/finding-out-the-area-of-a-triangle-if-the-coordinates-of-the-three-vertices-are
 */

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class BSGS {


    static char [][] matrix = null;


    public static int distance(int x1, int y1, int x2, int y2) {
        int distX = x2-x1;
        int distY = y2-y1;
        return (distX*distX) + (distY*distY);
    }

    public static int euclidianDistance(int x1, int y1, int x2, int y2) {
        int distX = x2-x1;
        int distY = y2-y1;
        return (int)Math.sqrt( (distX*distX) + (distY*distY));
    }


    public static void main(String[] args) {

    }

}
