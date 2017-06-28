import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by r028367 on 16/06/2017.
 *
 * https://www.hackerrank.com/challenges/a-circle-and-a-square
 */
public class CNS {

    static char [][] matrix;

    public static int euclidianDistance(int x1, int y1, int x2, int y2) {
        int distX = x2-x1;
        int distY = y2-y1;
        return (int) Math.sqrt( (distX*distX) + (distY*distY));
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
        int x1, y1, x2, y2;
        public Rect(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public int [] centerPoint() {
            return new int[] {(x2 + x1)/2, (y2 + y1)/2};
        }

        public int [] halfDiagonal() {
            return new int[] {(x1 - x2)/2, (y1 - y2)/2};
        }

        /**
         * https://math.stackexchange.com/questions/506785/given-two-diagonally-opposite-points-on-a-square-how-to-calculate-the-other-two
         * */
        public int [] getP2() {
            int hd [] = halfDiagonal();
            int cp [] = centerPoint();
            return new int[] {cp[0] - hd[1], cp[1] + hd[0]};
        }

        public int [] getP4() {
            int hd [] = halfDiagonal();
            int mp [] = centerPoint();
            return new int[] {mp[0] + hd[1], mp[1] - hd[0]};
        }


        static int [][] pMatRotate = {
                {(int) cos(toRadians(90)), (int) -sin(toRadians(90))}
                ,{(int) sin(toRadians(90)), (int)  cos(toRadians(90))}
        };

        static int [][] nMatRotate = {
                {(int)  cos(toRadians(90)), (int) sin(toRadians(90))}
                ,{(int) -sin(toRadians(90)), (int) cos(toRadians(90))}
        };

        public int aT(int x1, int y1, int x2, int y2, int x3, int y3) {
            int a = ((x1*y2)-(y1*x2)) + ((x2*y3)-(y2*x3)) + ((x3*y1)-(y3*x1));
            a /= 2;
            return a < 0 ? -a : a;
        }

        public int aR(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
            int a = ((x1*y2)-(y1*x2)) + ((x2*y3)-(y2*x3)) + ((x3*y4)-(y3*x4)) + ((x4*y1)-(y4*x1));
            a /= 2;
            return  a;
        }

        /**
         * Usando o metodo dos triangulos
         * */
        public  boolean insideRect(int x, int y) {
            int a [] = {x1, y1};
            int b [] = getP2();
            int c [] = {x2, y2};
            int d [] = getP4();

            int rectArea = BSGS.distance(a[0], a[1], c[0], c[1]) / 2;

            int ABPT = aT(a[0], a[1], b[0], b[1], x, y);
            int CBPT = aT(c[0], c[1], b[0], b[1], x, y);
            int ADPT = aT(a[0], a[1], d[0], d[1], x, y);
            //int ACPT = aT(a[0], a[1], c[0], c[1], x, y);
            //int CDPT = aT(c[0], c[1], d[0], d[1], x, y);
            //int BDPT = aT(b[0], b[1], d[0], d[1], x, y);

            int sum = (ABPT + CBPT + ADPT);
            if(sum > rectArea) {
                return false;
            }

            else if( sum == rectArea){
                if(ABPT * CBPT * ADPT == 0)
                    return true;
                else
                    return false;
            }

            return true;
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
        // circulo
        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                boolean valid = euclidianDistance(c.centerX, c.centerY, i, j) <= c.radius ||  r.insideRect(i, j);
                matrix[i][j]  = valid ? '#' : '.';
            }
        }
    }

    public static void reader() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), " ");
            int w = Integer.parseInt(tokenizer.nextToken());
            int h = Integer.parseInt(tokenizer.nextToken());

            int cx = Integer.parseInt(tokenizer.nextToken());
            int cy = Integer.parseInt(tokenizer.nextToken());
            int rd = Integer.parseInt(tokenizer.nextToken());
            Circle c = new Circle(cx, cy, rd);

            int px1 = Integer.parseInt(tokenizer.nextToken());
            int py1 = Integer.parseInt(tokenizer.nextToken());
            int px2 = Integer.parseInt(tokenizer.nextToken());
            int py2 = Integer.parseInt(tokenizer.nextToken());

            Rect r = new Rect(px1, py1, px2, py2);
            solver(w, h, c, r);

        } catch (Exception e) {}
    }


    private static void rotate() {
        Rect r = new Rect(2, 6, 8, 4);
        int p [] = r.rotate90(new int[] {r.x1, r.x1}, Rect.pMatRotate);
        System.out.printf("%d %d\n", p[0], p[1]);
        int n [] = r.rotate90(new int[] {r.x1, r.x1}, Rect.nMatRotate);
        System.out.printf("%d %d\n", n[0], n[1]);
    }


    private static void getPoints() {
        //Rect r = new Rect(2, 6, 10, 2);
        Rect r = new Rect(2, 6, 8, 4);
        //Rect r = new Rect(10, 0, 0, 10);
        int b [] = r.getP2();
        int p [] = r.getP4();
        System.out.printf("%d %d\n", b[0], b[1]);
        System.out.printf("%d %d\n", p[0], p[1]);

        System.out.println(r.insideRect(8, 4));
        System.out.println(r.insideRect(4, 2));
        System.out.println(r.insideRect(8, 4));
        System.out.println(r.insideRect(6, 8));
        System.out.println(r.insideRect(3, 3));
        System.out.println(r.insideRect(4, 1));
    }

    public static void main(String[] args) {

    }

}
