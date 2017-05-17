/**
 * Created by C.Lucas on 06/05/2017.
 */
public class FastDoublingFibonacci {
    /**
     * https://math.stackexchange.com/questions/975741/applying-fibonacci-fast-doubling-identities
     * https://www.hackerearth.com/practice/notes/fast-doubling-method-to-find-nth-fibonacci-number/
     * https://www.nayuki.io/res/fast-fibonacci-algorithms/fastfibonacci.py
     * https://math.stackexchange.com/questions/975741/applying-fibonacci-fast-doubling-identities
     *
     * exp algorithm
     * http://eli.thegreenplace.net/2009/03/21/efficient-integer-exponentiation-algorithms
     *
     * http://stackoverflow.com/questions/671815/what-is-the-fastest-most-efficient-way-to-find-the-highest-set-bit-msb-in-an-i
     * */

    public static long modularRecFastDoublingFibonacci(long n, long mod, long terms []) {
        if(n == 0) {
            terms[0] = 0;
            terms[1] = 1;
            return terms[1];
        }
        modularRecFastDoublingFibonacci(n/2, mod, terms);
        long a = terms[0];          // F(N)
        long b = terms[1];          // F(N+1)
        //long c = a*(2*b-a);       // F(2N)
        long c = ((a%mod) * ((((2%mod*b%mod)%mod)%mod - a%mod)%mod)%mod)%mod;
        //long d = (a*a)+(b*b);       // F(2N+1)
        long d = ((a%mod*a%mod)%mod)+((b%mod*b%mod)%mod);

        if( (n & 1) == 0 ) {
            terms[0] = c;
            terms[1] = d;
        }
        else {
            terms[0] = d;
            terms[1] = c+d;
        }

        return terms[0];
    }

    public static int msb(long m) {
        int pos = 62;
        long bit = (1L << pos);
        while ( (bit & m) == 0 && pos > 0) {
            bit = (1L << --pos);
        }
        return pos;
    }

    public static long modularItFastDoublingFibonacci(long m, long mod) {
        long a = 0, b = 1;
        for(long i=msb(m); i>=0; i--) {
            // F(2N)
            long c = ((a%mod)*((((2%mod*b%mod)%mod)%mod-a%mod)%mod)%mod)%mod;
            // F(2N+1)
            long d = ((a%mod*a%mod)%mod)+((b%mod*b%mod)%mod);
            a = c;
            b = d;
            long bit = m >> i;
            if ( (bit & 1) != 0 ) {
                long e = a + b;
                a = b;
                b = e;
            }
        }
        return  a;
    }

    public static void main(String[] args) {
        long mod = 300000L;
        /*
        for(int i=1; i<63; i++) {
            System.out.printf("%d %d", i, msb(1L << i));;
        }
        */
        //System.out.println( (1L << 63L) - 1);
        for(long i=1; i<500; i++) {
            long terms [] = {0,1};
            System.out.printf("%d %d %d\n", i, modularRecFastDoublingFibonacci(i, mod, terms)
                    , modularItFastDoublingFibonacci(i, mod));

        }
    }

}
