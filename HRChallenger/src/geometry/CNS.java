package geometry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.sqrt;

/**
 * Created by r028367 on 16/06/2017.
 *
 * https://www.hackerrank.com/challenges/a-circle-and-a-square
 */
public class CNS {

    static char [][] matrix;

    static double [][] positiveRotate = {
         {(int) cos(toRadians(90)),  -sin(toRadians(90))}
        ,{(int) sin(toRadians(90)), cos(toRadians(90))}
    };

    static double [][] negativeRotate = {
         { cos(toRadians(90)), sin(toRadians(90))}
        ,{-sin(toRadians(90)), cos(toRadians(90))}
    };


    public static double euclidianDistance(double x1, double y1, double x2, double y2) {
        double distX = x2-x1;
        double distY = y2-y1;
        return sqrt( (distX*distX) + (distY*distY));
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
        // pontos opostos na diagonal
        double x1, y1, x3, y3, x2, y2, x4, y4;
        public Rect(double x1, double y1, double x3, double y3) {
            this.x1 = x1;
            this.y1 = y1;
            this.x3 = x3;
            this.y3 = y3;
            double half []     = halfDiagonal();   // [x:0, y:1]
            double center []   = centerPoint();    // [x:0, y:1]
            this.x2 = center[0] - half[1];
            this.y2 = center[1] + half[0];
            this.x4 = center[0] + half[1];
            this.y4 = center[1] - half[0];
        }

        public double [] centerPoint() {
            return new double[] {(x3 + x1)/2, (y3 + y1)/2};
        }

        public double [] halfDiagonal() {
            return new double[] {(x1 - x3)/2, (y1 - y3)/2};
        }

        public double aT(double x1, double y1, double x2, double y2, double x3, double y3) {
            double a = ((x1*y2)-(y1*x2)) + ((x2*y3)-(y2*x3)) + ((x3*y1)-(y3*x1));
            a /= 2;
            return a < 0 ? -a : a;
        }

        /**
         * Area do retangulo
         * */
        public double aR(double x1, double y1, double x2, double y2
                , double x3, double y3, double x4, double y4) {
            double a = ((x1*y2)-(y1*x2)) + ((x2*y3)-(y2*x3)) + ((x3*y4)-(y3*x4)) + ((x4*y1)-(y4*x1));
            a /= 2;
            return  a;
        }

        // https://martin-thoma.com/how-to-check-if-a-point-is-inside-a-rectangle/
        public double aR2() {
            double a = (y3 - y1) * (x4 - x2) + (y2 - y4) * (x1 - x3);
            a /= 2;
            return  a < 0 ? -a : a;
        }

        public double aT2(double x1, double y1, double x2, double y2, double x3, double y3) {
            double a  = (x1*(y2 - y3) + x2*(y3 - y1) + x3*(y1-y2));
            a /= 2;
            return  a < 0 ? -a : a;
        }

        /**
         * Usando o metodo dos triangulos
         * */
        public  boolean insideRect(int x, int y) {
            /**
             * Conhecendo os pontos A e C, vamos achar
             * B e D
             * */
            // https://math.stackexchange.com/questions/506785/given-two-diagonally-opposite-points-on-a-square-how-to-calculate-the-other-two

            double ar = aR2();
            /**
             * triangulos ABP, BCP, CDP e ADP
             * */
            // ABP
            double abp = aT2(x1, y1, x2, y2, x, y);
            // bcp
            double bcp = aT2(x2, y2, x3, y3, x, y);
            //cdp
            double cdp = aT2(x3, y3, x4, y4, x, y);
            // adp
            double adp = aT2(x1, y1, x4, y4, x, y);
            return ar == adp+bcp+cdp+adp;
        }

        public int [] rotate90(int vect[], int matrixRotate [][]) {
            int vectR [] = new int[] {0, 0};
            for (int i = 0; i < 2 ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    vectR[i] += vect[j] * matrixRotate[j][i];
                }
            }
            return vectR;
        }
    }

    public static void solver(int w, int h, Circle c, Rect r) {
        matrix = new char[h][w];
        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                /**
                 * Se o pixel estiver a uma distancia <= ao raio do circulo
                 * ou dentro do quadrado eh um pixel pixel da imagem '#' senao
                 * eh um pixel de fundo '.'
                 * */
                boolean inside = euclidianDistance(c.centerX, c.centerY, i, j) <= c.radius ||  r.insideRect(i, j);
                matrix[i][j]  = inside ? '#' : '.';
            }
        }
    }

    public static void reader() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), " ");
            // dimensao da imagem
            int w = Integer.parseInt(tokenizer.nextToken());
            int h = Integer.parseInt(tokenizer.nextToken());
            // parametros do circulo, centro e raio
            int cx = Integer.parseInt(tokenizer.nextToken());
            int cy = Integer.parseInt(tokenizer.nextToken());
            int rd = Integer.parseInt(tokenizer.nextToken());
            Circle c = new Circle(cx, cy, rd);
            // pontos na diagonal do quadrilatero
            int px1 = Integer.parseInt(tokenizer.nextToken());
            int py1 = Integer.parseInt(tokenizer.nextToken());
            int px2 = Integer.parseInt(tokenizer.nextToken());
            int py2 = Integer.parseInt(tokenizer.nextToken());
            Rect r = new Rect(px1, py1, px2, py2);
            solver(w, h, c, r);
        } catch (Exception e) {}
    }



    private static void getPoints() {
        //Rect r = new Rect(2, 6, 10, 2);
        Rect r = new Rect(2, 6, 8, 4);
        //Rect r = new Rect(2, 5, 8, 5);
        System.out.println(r.insideRect(4, 1));
        System.out.println(r.insideRect(8, 4));
        System.out.println(r.insideRect(4, 2));
        System.out.println(r.insideRect(8, 4));
        System.out.println(r.insideRect(6, 8));
        System.out.println(r.insideRect(3, 3));
    }

    public static void main(String[] args) {
        getPoints();
    }

}
