package geometry.medium;

/**
 * Created by C_Luc on 20/08/2017.
 */
public class EuclidianAlgorithm {
    public static long gdc(long a, long b) {
        return a % b == 0 ? b : gdc (b, a % b);
    }

    public static long gdc2(long a, long b) {
        while( a % b > 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return b;
    }

    // verificar se a equacao linear (ax+by=c) possui solucao para (x, y)
    public static boolean verify(long a, long b, long c) {
        return  c % gdc(a, b) == 0;
    }

    /**
     * Euclidiab algorithm GDC
     * ax + by = gcd(a, b)
     * https://brilliant.org/wiki/extended-euclidean-algorithm/
     * http://www-math.ucdenver.edu/~wcherowi/courses/m5410/exeucalg.html
     * */


    private static long [] extededGDCIt(long a, long b) {
        long  s = 0
            , t = 1
            , oldS = 1     // temp Y
            , oldT = 0;    // temp X
        long r = b, oldR = a;
        while(r > 0) {
            long q = oldR / r;               // quociente
            long remainder = oldR % r;       // resto oldR - q * r
            oldR = r;
            r = remainder;
            long u = s - q * oldS;
            long v = t - q * oldT;
            s = oldS;
            t = oldT;
            oldS = u;
            oldT = v;
        }
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", a, b, oldR, t, s);
        return new long [] {oldR, t, s};
    }

    private static long [] extededGDCRec(long a, long b) {
        if(a % b == 0) {
            return new long[] {b, 0, 1};
        }
        long [] result ;
        result = extededGDCRec(b, a%b);
        long u = result[1], v = result[2];
        result[1] = v;
        result[2] = u - (a/b) * v;
        return result;
    }

    private static void extendedGDC(long a, long b, long [] result) {
        if(a % b == 0) {
            result[0] = b;
            result[1] = 0;
            result[2] = 1;
            return;
        }
        extendedGDC(b, a%b, result);
        long u = result[1];
        long v = result[2];
        result[1] = v;
        result[2] = u - (a/b) * v;
        return;
    }

    public static void test() {
        System.out.printf("%d %d\n", gdc(408, 126), gdc2(408, 126));
        System.out.printf("%d %d\n", gdc(102, 38), gdc2(102, 38));
        System.out.printf("%d %d\n", gdc(126, 30), gdc2(126, 30));
        System.out.printf("%d %d\n", gdc(81, 57), gdc2(81, 57));
        System.out.printf("%d %d\n", gdc(42823, 6409), gdc2(42823, 6409));
        System.out.printf("%d %d\n", gdc(1914, 899), gdc2(1914, 899));
        System.out.printf("%d %d\n", gdc(1232, 573), gdc2(1232, 573));
    }

    private static void test2() {
        extededGDCIt(12, 2);
        extededGDCIt(408, 126);
        extededGDCIt(102, 38);
        extededGDCIt(126, 30);
        extededGDCIt(81, 57);
        extededGDCIt(42823, 6409);
        extededGDCIt(1914, 899);
        System.out.println("Resultados do algoritmo recursiveo\n\n");

        long rs [];
        rs = extededGDCRec(12, 2);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 12, 2, rs[0], rs[1], rs[2]);
        rs = extededGDCRec(408, 126);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 408, 123, rs[0], rs[1], rs[2]);
        rs = extededGDCRec(102, 38);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 102, 38, rs[0], rs[1], rs[2]);
        rs = extededGDCRec(81, 57);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 81, 57, rs[0], rs[1], rs[2]);
        rs = extededGDCRec(42823, 6409);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 42823, 6409, rs[0], rs[1], rs[2]);
        rs = extededGDCRec(1914, 899);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 1914, 899, rs[0], rs[1], rs[2]);
    }

    private static void test3() {
        long rs [] = new long[3];
        extendedGDC(12, 2, rs);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 12, 2, rs[0], rs[1], rs[2]);
        extendedGDC(408, 126, rs);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 408, 126, rs[0], rs[1], rs[2]);
        extendedGDC(102, 38, rs);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 102, 38, rs[0], rs[1], rs[2]);
        extendedGDC(81, 57, rs);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 81, 57, rs[0], rs[1], rs[2]);
        extendedGDC(42823, 6409, rs);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 42823, 6409, rs[0], rs[1], rs[2]);
        extendedGDC(1914, 899, rs);
        System.out.printf("GDC(%d,%d)=%d. Solucaoo ax+by=c: x(%d) y(%d)\n", 1914, 899, rs[0], rs[1], rs[2]);
    }


    public static void main(String[] args) {
        test3();
    }
}
