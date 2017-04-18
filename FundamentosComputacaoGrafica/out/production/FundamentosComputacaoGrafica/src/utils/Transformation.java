package utils;

/**
 * Created by C.Lucas on 04/04/2017.
 */

import static java.lang.Math.*;


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
    public static double[][] matrixRotationDegree(double degree) {
        return new double [][] {
             {floor(cos(toDegree(degree))), floor(sin(toDegree(degree)))}
            ,{floor(-sin(toDegree(degree))), floor(cos(toDegree(degree)))}
        };
    }


    public static double[][] matrixRotationRadian(double degree) {
        return new double [][] {
             {floor(cos(toRadian(degree))), floor(sin(toRadian(degree)))}
            ,{floor(-sin(toRadian(degree))),floor(cos(toRadian(degree)))}
        };
    }

    public static double[][] matrixScale2D(double scaleX, double scaleY) {
        return new double [][] {
             {scaleX, 0}
            ,{0, scaleY}
        };
    }

    public static double[][] matrixScale2D(double scaleX, double scaleY, double scaleZ) {
        return new double [][] {
             {scaleX, 0, 0}
            ,{0, scaleY, 0}
            ,{0, 0, scaleZ}
        };
    }

    public static double [] rotate2D(double degree, double point []) {
        double rotation [][] = matrixRotationRadian(degree);
        int l = rotation.length, c = rotation[0].length;
        double newPoints [] = new double[2];
        for(int i=0; i<1; i++) {     // colunas do vetor
            for(int j=0; j<c; j++) {            // linhas do vetor
                for(int k=0; k<l; k++) {        // linhas da matriz
                    newPoints [j] += point[k] * rotation[k][j];
                }
            }
        }
        return newPoints;
    }


    /**
     * http://stackoverflow.com/questions/36562488/2d-point-rotation-based-off-a-matrix
     * */
    public static void main(String[] args) {
        System.out.println(Math.floor(cos(toRadian(-90))));
        rotate2D(-90, new double[] {4, 0});
        rotate2D(-90, new double[] {0, -4});
    }
}
