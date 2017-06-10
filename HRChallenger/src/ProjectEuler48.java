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

    // avoid overflow
    public static long expmod2(long base, long exp, long mod) {
        base %= mod;
        long p = 0;
        while(exp>0) {
            if((exp & 1) == 1) {
               //p = summod(p, base, mod);
               p = (p+base)%mod;
            }
            //base = multmod(base, 2, mod);
            base = (base * 2) % mod;
            exp >>= 1;
        }
        return p % mod;
    }


    public static long expmod3(long base, long exp, long m) {
        base %= m;
        exp %= m;
        long p = 0;
        while(exp>0) {
            if((exp & 1) == 0) {
                p = ((m-p) > base) ? p+base : p+base-m;
            }
            exp >>= 1;
            if(exp == 0) {
                base =  ((m-base) > base) ? base*2 : base*2-m;
            }
        }
        return p;
    }

    public static final void test() {
        long acc = 0;
        for (int i=1; i<200000; i++) {
            acc = summod(acc, expmod3(i, i, M), M);
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
        //solver();
        //test();
        /*
        System.out.println(expmod(9223372036854775807l, 9223372036854775807l, 100000000000l));
        System.out.println(expmod2(9223372036854775807l, 9223372036854775807l, 100000000000l));
        */
        System.out.println(expmod(10, 12, 100000000000l));
        System.out.println(expmod(10, 12, 150));
        System.out.println(expmod2(10, 12, 100000000000l));
        System.out.println(expmod2(10, 12, 150));
        System.out.println(expmod3(10, 12, 150));
    }
}
