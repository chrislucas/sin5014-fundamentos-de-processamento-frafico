package DP;

import java.math.BigInteger;

/**
 * Created by r028367 on 25/04/2017.
 * https://www.hackerearth.com/practice/algorithms/dynamic-programming/introduction-to-dynamic-programming-1/practice-problems/algorithm/angry-neighbours/
 */
public class WackyWorkouts {

    public static long MOD = 1000000007;
    public static long modularMultiply(long a, long b) {
        return ((a % MOD) * (b % MOD)) % MOD;
    }
    static long nth(long n) {
        long f[][] = {{1,1},{1,0}};
        long f0[][] = {{1,1},{1,0}};
        for(int i=0; i<=n; i++){
            long a = modularMultiply(f[0][0], f0[0][0]) + modularMultiply(f[0][1], f0[1][0]);
            long b = modularMultiply(f[0][0], f0[0][1]) + modularMultiply(f[0][1], f0[1][1]);
            long c = modularMultiply(f[1][0], f0[0][0]) + modularMultiply(f[1][1], f0[1][0]);
            long d = modularMultiply(f[1][0], f0[0][1]) + modularMultiply(f[1][1], f0[1][1]);
            f[0][0] = a;
            f[0][1] = b;
            f[1][0] = c;
            f[1][1] = d;
        }
        return f[0][1];
    }

    public static final BigInteger O = BigInteger.ONE, Z = BigInteger.ZERO, P = new BigInteger("1000000007");

    public static BigInteger modularMultiply(BigInteger A, BigInteger B, BigInteger MOD) {
        return (A.mod(MOD).multiply(B.mod(MOD))).mod(MOD);
    }

    public static BigInteger nth(BigInteger n) {
        BigInteger f[][] = { {O, O}, {O, Z} };
        BigInteger g[][] = { {O, O}, {O, Z} };
        for (BigInteger i = Z; i.compareTo(n.add(O)) < 0; i = i.add(O)) {
            BigInteger a = modularMultiply(f[0][0], g[0][0], P).add(modularMultiply(f[0][1], g[1][0], P));
            BigInteger b = modularMultiply(f[0][0], g[0][1], P).add(modularMultiply(f[0][1], g[1][1], P));
            BigInteger c = modularMultiply(f[1][0], g[0][0], P).add(modularMultiply(f[1][1], g[1][0], P));
            BigInteger d = modularMultiply(f[1][0], g[0][1], P).add(modularMultiply(f[1][1], g[1][1], P));
            f[0][0] = a;
            f[0][1] = b;
            f[1][0] = c;
            f[1][1] = d;
        }
        return f[0][1];
    }

    public static long optimazed(long n) {
        return 0;
    }



    public static long testRecurrence(long n) {
        return 0;
    }

    public static void main(String[] args) {
        BigInteger integer = new BigInteger("9");
        System.out.println(nth( integer ));
    }

}
