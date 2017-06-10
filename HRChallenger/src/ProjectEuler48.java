import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by r028367 on 08/06/2017.
 *
 * https://www.hackerrank.com/contests/projecteuler/challenges/euler048
 * https://www.hackerrank.com/challenges/power-of-large-numbers
 * https://www.hackerrank.com/challenges/restaurant
 *
 */
public class ProjectEuler48 {

    public static final long M = 10000000000l;

    public static long multmod(long a, long b, long m) {
        return ((a % m) * (b % m)) % m;
    }
    public static long summod(long a, long b, long m) {
        return ((a % m) + (b % m)) % m;
    }

    public static long expmod(long base, long exp, long mod) {
        long p = 1;
        while (exp > 0) {
            if( (exp & 1) == 1 ) {
                p = multmod(p, base, mod);
            }
            base = multmod(base, base, mod);
            exp >>= 1;
        }
        return p;
    }

    public static final void test() {
        long acc = 0;
        for (int i=1; i<200000; i++) {
            acc = summod(acc, expmod(i, i, M), M);
        }
        System.out.println(acc);
    }

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            long n = Long.parseLong(bufferedReader.readLine());
            long acc = 0;
            for (int i=17; i<=n; i++) {
                long exp = expmod(i, i, M);
                acc = summod(acc, exp, M);
            }
            System.out.println(acc);
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        solver();
    }
}
