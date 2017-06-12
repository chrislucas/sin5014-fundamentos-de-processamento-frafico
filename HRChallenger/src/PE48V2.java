import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.BitSet;

/**
 * Created by r028367 on 12/06/2017.
 */
public class PE48V2 {

    public static BigInteger summod(BigInteger A, BigInteger B, BigInteger M) {
        BigInteger S = A.mod(M).add(B.mod(M));
        return S.mod(M);
    }

    public static BigInteger multmod(BigInteger A, BigInteger B, BigInteger M) {
        BigInteger S = A.mod(M).multiply(B.mod(M));
        return S.mod(M);
    }

    public static BigInteger expmod(BigInteger base, BigInteger exp, BigInteger M) {
        BigInteger p = BigInteger.ONE;
        while (exp.compareTo(BigInteger.ZERO) == 1) {
            if( exp.and( BigInteger.ONE).equals( BigInteger.ONE)  ) {
                p = multmod(p, base, M);
            }
            base = multmod(base, base, M);
            exp  = exp.shiftRight(1);
        }
        //return p;
        return p.mod(M);
    }

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            long n = Long.parseLong(bufferedReader.readLine());
            BigInteger acc = BigInteger.ZERO;
            BigInteger m = new BigInteger("10000000000");
            for (int i=1; i<=n; i++) {
                BigInteger base = new BigInteger(String.valueOf(i));
                BigInteger exp = new BigInteger(String.valueOf(i));
                acc = summod(acc, expmod(base, exp, m), m);
            }
            System.out.println(acc);
        } catch (IOException ioex) {}
    }


    public static void test() {
        BigInteger a = new BigInteger("9223372036854775807");
        BigInteger b = new BigInteger("9223372036854775807");
        BigInteger m = new BigInteger("100000000000");
        System.out.println(expmod(a, b, m));
    }


    public static void main(String[] args) {
        //test();
        solver();
    }
}
