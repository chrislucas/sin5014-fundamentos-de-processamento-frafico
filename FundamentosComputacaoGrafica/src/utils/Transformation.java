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


    public static void test() {
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

    public static double[][] matrixRotation2DDegree(double radians) {
        return new double [][] {
             {cos(toDegree(radians)), sin(toDegree(radians))}
            ,{-sin(toDegree(radians)), cos(toDegree(radians))}
        };
    }

    public static double [][] getHomogeneousMatrixRotation2DDegree(double radians) {
        return new double [][] {
             {cos(toRadian(radians)), sin(toRadian(radians)) , 0}
            ,{-sin(toRadian(radians)),cos(toRadian(radians)), 0}
            ,{0, 0, 1}
        };
    }


    /**
     * Rotacao no sentido horario
     * https://www.idomaths.com/linear_transformation.php
     * [
     *  [cos(theta), sin(theta)]
     *  [-sin(theta), cos(theta)]
     * ]
     * Rotacao no sentido anti horario
     * {
     *   [cos(theta), -sin(theta)]
     *  [sin(theta), cos(theta)]
     * }
     * */
    public static double[][] matrixRotation2DRadian(double degree) {
        return new double [][] {
             {cos(toRadian(degree)), sin(toRadian(degree))}
            ,{-sin(toRadian(degree)),cos(toRadian(degree))}
        };
    }

    public static double [][] getHomogeneousMatrixRotation2DRadian(double degree) {
        return new double [][] {
             {cos(toRadian(degree)), sin(toRadian(degree)) , 0}
            ,{-sin(toRadian(degree)),cos(toRadian(degree)), 0}
            ,{0, 0, 1}
        };
    }

    /**
     * Matrix homogenea para rotacao em 3D no eixo X
     *
     * */
    public static double [][] getHomogeneousMatrixRotationX(double degree) {
        return new double [][] {
                 {1, 0, 0, 0}
                ,{0, cos(toRadian(degree)), sin(toRadian(degree)), 0}
                ,{0, -sin(toRadian(degree)), cos(toRadian(degree)), 0}
                ,{0, 0, 0, 1}
        };
    }
    /**
     *
     * No eixo Y
     * */
    public static double [][] getHomogeneousMatrixRotationY(double degree) {
        return new double [][] {
             {cos(toRadian(degree)), 0, -sin(toRadian(degree)), 0}
            ,{0, 1, 0, 0}
            ,{sin(toRadian(degree)), 0, cos(toRadian(degree)), 0}
            ,{0, 0, 0, 1}
        };
    }
    /**
     *
     * No eixo Z
     * */
    public static double [][] getHomogeneousMatrixRotationZ(double degree) {
        return new double [][] {
             {cos(toRadian(degree)), 0, sin(toRadian(degree)), 0}
            ,{-sin(toRadian(degree)),cos(toRadian(degree)), 0, 0}
            ,{0, 0, 1, 0}
            ,{0, 0, 0, 1}
        };
    }

    public static double [][] getHomogenousTransla3DteMatrix(double x, double y, double z) {
        return new double [][] {
             {1,0,0,0}
            ,{0,1,0,0}
            ,{0, 0, 1, 0}
            ,{x, y, z, 1}
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
        double rotation [][] = matrixRotation2DRadian(degree);
        int l = rotation.length, c = rotation[0].length;
        double newPoints [] = new double[2];
        for(int i=0; i<1; i++) {                // colunas do vetor
            for(int j=0; j<c; j++) {            // linhas do vetor
                for(int k=0; k<l; k++) {        // linhas da matriz
                    newPoints [j] += point[k] * rotation[k][j];
                }
            }
        }
        return newPoints;
    }

    public static double [] rotate3D(double degree, int axis, double point []) {
        double matrixRotate [][] = new double[4][4];
        switch (axis) {
            case 1:
                matrixRotate =  getHomogeneousMatrixRotationX(degree);
                break;
            case 2:
                matrixRotate =  getHomogeneousMatrixRotationY(degree);
                break;
            case 3:
                matrixRotate =  getHomogeneousMatrixRotationZ(degree);
                break;
            default:
                matrixRotate =  getHomogeneousMatrixRotationX(degree);
        }
        double newPoint [] = new double[4];

        int limX = matrixRotate.length, lixY = matrixRotate[0].length;

        return newPoint;
    }

    /**
     * o valor de point deve ser [x, y, 1]
     * */
    public static double [] homogeneousMatrixrotate2D(double degree, double point []) {
        double rotation [][] = matrixRotation2DRadian(degree);
        int l = rotation.length, c = rotation[0].length;
        double newPoints [] = new double[2];
        for(int i=0; i<1; i++) {                // linhas do vetor
            for(int j=0; j<c; j++) {            // colunas do vetor
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
        /*
        System.out.println(Math.floor(cos(toRadian(-90))));
        rotate2D(-90, new double[] {4, 0});
        rotate2D(-90, new double[] {0, -4});
        */
        executeExercise();
    }

    /**
     * matriz * matriz
     * */
    public static double [][] fmm(double [][] A, double B[][]) {
        int linA = A.length, colB = B[0].length;
        int colA = A[0].length, linB = B.length;
        double C[][] = new double[linA][colB];
        for(int i=0; i<linA; i++) {
            for(int j=0; j<colA; j++) { // ou k < linB, pois colA = linB, numa multiplicacao valida
                for(int k=0; k<linB; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    /**
     * vetor * matriz
     * */
    public static double [] fmvm(double [] A, double B[][]) {
        int l = B.length, c = B[0].length;
        double C [] = new double[A.length];
        for(int i=0; i<1; i++) {                // linhas do vetor
            for(int j=0; j<c; j++) {            // colunas do vetor B[0].length == A.length
                for(int k=0; k<l; k++) {        // linhas da matriz
                    C[j] += A[k] * B[k][j];
                }
            }
        }
        return C;
    }

    /**
     * Exercicio 4 aula
     * */
    private static void executeExercise() {
        double point [] = {-1.5, 2, 4, 1};       // vetor com ua dimensao a mais
        // rotacionar x a 15 graus
        // transaladar [2,-2,4]
        // rotacionar y a 45 gruas
        /*
        // so para testar
        double newMatrix [][] = fmm(fmm(getHomogeneousMatrixRotationX(15), getHomogenousTransla3DteMatrix(2,-2,4)), getHomogeneousMatrixRotationY(45));
        for(int i=0; i<newMatrix.length; i++) {
            for(int j=0; j<newMatrix[0].length; j++) {
                System.out.printf("%f ", newMatrix[i][j]);
            }
            System.out.println("");
        }
        double newPoint [] = fmvm(point, newMatrix);
        */
        double newPoint [] = fmvm(point, fmm(fmm(getHomogeneousMatrixRotationX(15), getHomogenousTransla3DteMatrix(2,-2,4)), getHomogeneousMatrixRotationY(45)));
        System.out.println("\n");
        for (int i=0; i<newPoint.length; i++) {
            System.out.printf("%f ", newPoint[i]);
        }
        System.out.println("\n");
    }
}
