import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 02/05/2017.
 */
public class URI1531 {

    public static final int MOD = 1000000009;

    public static long modularMultiply(long a, long b, long m) {
        return ((a % m) * (b % m)) % m;
    }

    public static long modularSum(long a, long b, long m) {
        return ((a % m) + (b % m)) % m;
    }

    private static long multiplyMatrixFibonacci(long f[][], long g[][], long m) {
        long a = modularSum(modularMultiply(f[0][0], g[0][0], m), modularMultiply(f[0][1], g[1][0], m), m);
        long b = modularSum(modularMultiply(f[0][0], g[0][1], m), modularMultiply(f[0][1], g[1][1], m), m);
        long c = modularSum(modularMultiply(f[1][0], g[0][0], m), modularMultiply(f[1][1], g[1][0], m), m);
        long d = modularSum(modularMultiply(f[1][0], g[0][1], m), modularMultiply(f[1][1], g[1][1], m), m);
        f[0][0] = a;
        f[0][1] = b;
        f[1][0] = c;
        f[1][1] = d;
        return f[0][1];
    }

    private static long nthRecFibonacciLogn(long matA [][], long p, long m) {
        if(p == 0 || p == 1)
            return matA[0][1];
        nthRecFibonacciLogn(matA, p/2, m);
        multiplyMatrixFibonacci(matA, matA, m);
        if((p&1)==1) {
            multiplyMatrixFibonacci(matA, new long[][]{{1,1},{1,0}}, m);
        }
        return matA[0][1];
    }

    //  Fib(K)%M  =  Fib(K%C)%M
    //  http://webspace.ship.edu/msrenault/fibonacci/fiblist.htm
    public static long getPisanoPeriod(long m) {
        long period = 1;
        long table [] =  {0, 1};
        if(m > 1) {
            for(long i=2; ; i++) {
                table[0] = ((table[1] % m) - (table[0] % m)) % m;
                table[1] = ((table[1] % m) + (table[0] % m)) % m;
                if(table[0] == 0 && table[1] == 1)
                    break;
                period++;
            }
        }
        return period;
    }

    private static long nthItFibonacciLogn(long matA [][], long p, long mod) {
        Stack<Long> stack = new Stack<>();
        for(long i=p; i>1; i/=2) {
            stack.add(i);
        }
        while(!stack.empty()) {
            long i = stack.pop();
            multiplyMatrixFibonacci(matA, matA, mod);
            if((i&1)==1) {
                multiplyMatrixFibonacci(matA, new long[][]{{1,1},{1,0}}, mod);
            }
        }
        return matA[0][1];
    }

    // https://www.urionlinejudge.com.br/judge/pt/problems/view/1531
    // solver

    public static long solver(long n, long mod) {
        /**
         * Resolver
         * FIB(FIB(n)) % m
         * Fib(n)%m = Fib(n%c)%m
         * */
        long matA[][] = new long[][]{{1,1},{1,0}};
        long cycle    = getPisanoPeriod(mod);
        //long i        = /*nthRecFibonacciLogn*/nthItFibonacciLogn(matA, n%cycle, mod);
        long i        = nth2(n, cycle);
        matA          = new long[][]{{1,1},{1,0}};
        //long j        = /*nthRecFibonacciLogn*/nthItFibonacciLogn(matA, i%cycle, mod);
        long j        = nth2(i, cycle);
        return j;
    }



    public static void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String in = "";
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
            while ( (in = bufferedReader.readLine()) != null ) {
                StringTokenizer tokenizer = new StringTokenizer(in, " ");
                long n = Long.parseLong(tokenizer.nextToken());
                long mod = Long.parseLong(tokenizer.nextToken());
                printWriter.printf("%d\n", solver(n, mod));
            }
        } catch (IOException ioex) {}
    }

    public static void test() {
        for(long i=1; i<100; i++) {
            long matA[][] = new long[][]{{1,1},{1,0}};
            System.out.printf("%d %d\n", i, nthItFibonacciLogn(matA, i, 3000000));
        }
    }

    public static void main(String[] args) {
        run();
    }

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

    public static long fastDoublingFibonacci(long n, long mod) {
        return 0;
    }

    /**
     * https://github.com/gustavosm/uri/blob/master/1531.cpp
     * */
    public static long nth2(long n, long mod) {
        if(n < 3) {
            return 1L;
        }
        else {
            long msb = 63;
            while (((1 << (msb-1) & n)) == 0 && msb >= 0)
                msb--;
            long table [] = {0, 1};
            for(long i=msb; i>=0; i--) {

            }
            return table[0];
        }
    }
}
