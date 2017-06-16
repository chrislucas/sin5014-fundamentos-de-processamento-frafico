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
 * https://benpyeh.com/2013/03/15/project-euler-48-self-powers/
 *
 */
public class ProjectEuler48 {

    public static final long M = 10000000000L;

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
        //return p;
        return p%mod;
    }

    public static void test() {
        long acc = 0;
        for (int i=1; i<200000; i++) {
            acc = summod(acc, Experimental.modpow(i, i, M), M);
        }
        System.out.println(acc);
    }

    public static void test2() {
        System.out.println(expmod(9223372036854775807l, 9223372036854775807l, 100000000000l));
        System.out.println(Experimental.modpow(9223372036854775807l, 9223372036854775807l, 100000000000l));
    }

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            long n = Long.parseLong(bufferedReader.readLine());
            long acc = 0;
            for (int i=1; i<=n; i++) {
                long exp = Experimental.modpow(i, i, M);
                //long exp = expmod(i, i, M);
                acc = summod(acc, exp, M);
            }
            System.out.println(acc);
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        // http://comnuan.com/cmnn02/cmnn02008/
        solver();
    }


    public static class Experimental {
        // avoid overflow
        static long modpow(long a, long b, long mod)
        {
            long product,pseq;
            product=1;
            pseq=a%mod;
            while(b>0)
            {
                if((b&1) == 1)
                    product=modmult(product,pseq,mod);
                pseq=modmult(pseq,pseq,mod);
                b>>=1;
            }
            return product;
        }

        static long modmult( long a, long b, long mod)
        {
            if (a == 0 || b < mod / a) {
                return (a%mod*b%mod)%mod;
            }
            long sum = 0;
            while(b>0)
            {
                if((b&1) == 1)
                    sum = (sum + a) % mod;
                a = (2*a) % mod;
                b>>=1;
            }
            return sum;
        }
    }
}
