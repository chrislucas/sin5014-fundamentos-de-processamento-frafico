package transformation;

/**
 * Created by C.Lucas on 04/04/2017.
 */

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;

public class Transformation {


    public static double toRadian(double degree) {
        return degree * Math.PI / 180.0 ;
    }


    public static double toDegree(double radians) {
        return radians * 180 / Math.PI;
    }

/*
    public static void main(String[] args) {

        System.out.println(Math.tan(toRadian(45)));
        System.out.println(toDegree(toRadian(45)));
        for(int i=-180; i<361; i++) {
            System.out.printf("%d %f\n", i, sin(toRadian(i)));
        }

        double p1 [] = {2, 2};
        double p2 [] = {10, 10};
        double np1 [] = rotate2D(135, p1);
        double np2 [] = rotate2D(135, p2);
        System.out.printf("%f %f %f %f", np1[0], np1[1], np2[0], np2[1]);
    }
*/
    public static double[][] matrixRotation(double degree) {
        return new double [][] {
             {cos(toRadian(degree)), sin(toRadian(degree))}
            ,{-sin(toRadian(degree)), cos(toRadian(degree))}
        };
    }

    public static double [] rotate2D(double degree, double point []) {
        double rotation [][] = matrixRotation(degree);
        int l = rotation.length, c = rotation[0].length;
        double newPoints [] = new double[2];
        for(int i=0; i<point.length; i++) {
            for(int j=0; j<c; j++) {
                for(int k=0; k<l; k++) {
                    newPoints [i] += point[k] * rotation[k][j];
                }
            }
        }
        return newPoints;
    }



}
