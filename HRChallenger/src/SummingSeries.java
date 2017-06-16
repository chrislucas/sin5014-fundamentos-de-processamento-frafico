import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by r028367 on 08/06/2017.
 * https://www.hackerrank.com/challenges/summing-the-n-series?h_r=next-challenge&h_v=zen
 * DONE
 */
public class SummingSeries {

    public static final long MOD = (long)1E9 + 7;
    // n^2-(n-1)^2

    public static long pow2mod(long n, long m) {
        return  ((n % m) * (n % m)) % m;
    }

    public static long plusmod(long a, long b, long m) {
        return ((a%m) + (b%m)) % m ;
    }

    public static long minusmod(long a, long b, long m) {
        return ((a%m) - (b%m)) % m ;
    }

    public static long solver(long n) {
        long acc = 0;
        for(long m=1; m<=n; m++) {
            long a = pow2mod(m, MOD);
            long b = pow2mod((m-1), MOD);
            long r = minusmod(a, b, MOD);
            acc = plusmod(acc, r, MOD);
        }
        return acc;
    }


    /**
     * Estudar a prova da progressão aritmética
     * https://en.wikipedia.org/wiki/Arithmetic_progression
     * https://www.hackerrank.com/challenges/summing-the-n-series/forum
     * exemplo do forum prova por inducao
     * https://en.wikipedia.org/wiki/Telescoping_series
     * */

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            for(;cases>0;cases--) {
                long value = Long.parseLong(bufferedReader.readLine());
                System.out.println(pow2mod(value, MOD));
            }
        } catch (IOException eiox) {}
    }
}
