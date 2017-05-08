import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 02/05/2017.
 * DONE
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

    private static long modularExpMatrix(long e, long f[][], long g[][], long mod ) {
        Stack<Long> stack = new Stack<>();
        for(long i=e; i>1; i/=2) {
            stack.add(i);
        }
        while(!stack.empty()) {
            long i = stack.pop();
            f = multMatrixFibonacci(f, f, mod);
            if((i&1)==1) {
                f = multMatrixFibonacci(f, g, mod);
            }
        }
        return f[0][1];
    }

    private static long[][] multMatrixFibonacci (long f[][], long g[][], long mod) {
        long h[][] = new long[f.length][g[0].length];
        int c = f[0].length, l = g.length;
        for(int i=0; i<c; i++) {
            for(int j=0; j<l; j++) {
                for(int k=0; k<c; k++) {
                    long m = (f[i][k]%mod * g[k][j]%mod)%mod;
                    h[i][j] = (h[i][j]%mod+m%mod)%mod;
                }
            }
        }
        return h;
    }

    private static long nthRecFibonacciLogn(long matA [][], long p, long m) {
        if(p == 0 || p == 1)
            return matA[0][1];
        nthRecFibonacciLogn(matA, p/2, m);
        multiplyMatrixFibonacci(matA, matA, m);
        if((p&1)==1) {
            multiplyMatrixFibonacci(matA, new long[][]{{1,1},{1,0}}, m);
        }
        return matA[1][0];
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

    public static long getPisanoPeriod2(long m) {
        long period = 1;
        long a = 0; long b = 1;
        if(m > 1) {
            for(long i=0; ; i++) {
                long T = (a % m + b % m/*a + b*/) % m;
                a = b;
                b = T;
                if(a == 0 && b == 1)
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
        long cycle    = getPisanoPeriod2(mod);
        long i        = modularExpMatrix(n, matA, new long[][]{{1,1},{1,0}}, cycle);
        // Nao funciona essa multiplicacao de matriz
        //long i        = nthRecFibonacciLogn(matA, n, cycle);
        // essa abordagem funciona
        //long i        = modularItFastDoublingFibonacci(n, cycle);
        matA          = new long[][]{{1,1},{1,0}};
        long j        = modularExpMatrix(i, matA, new long[][]{{1,1},{1,0}}, mod);
        // NEM Essa
        //long j        = nthRecFibonacciLogn(matA, i, mod);
        // Essa tabe,
        //long j        = modularItFastDoublingFibonacci(i, mod);
        return j;
    }

    public static void run() {
        //testPisanoMethod();
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

    public static void testPisanoMethod() {
        /*
        for(long i=1000000; i<1010000; i++) {
            if(getPisanoPeriod(i) < 0) {
                System.out.printf("Primeira versão %d", i);
            }
            if(getPisanoPeriod2(i) < 0) {
                System.out.printf("Segunda versão %d", i);
            }
            //System.out.printf("%d %d %d\n", i, getPisanoPeriod(i), getPisanoPeriod2(i));
        }
        */
        long constant[][] = {{1,1}, {1,0}};
        for(int i=0; i<100; i++) {
            System.out.printf("%d %d %d\n", i
                    ,modularExpMatrix(i, new long[][]{{1,1}, {1,0}}, constant, 3000000L)
                    ,nthRecFibonacciLogn(new long[][]{{1,1}, {1,0}}, i, 3000000L));
        }
        System.out.println("FIM");
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


    public static long modularItFastDoublingFibonacci(long m, long mod) {
        long a = 0, b = 1;
        int pos = 63;
        long bit = (1L << pos-1);
        while ( (bit & m) == 0 && pos > 0) {
            bit = (1L << --pos);
        }
        //for(long i=pos; i>=0; i--) {}
        for(long i=pos; i>=0; i--) {
            /*
            // F(2N)
            long c = ((a%mod)*((((2%mod*b%mod)%mod)%mod-a%mod)%mod)%mod)%mod;
            // F(2N+1)
            long d = ((a%mod*a%mod)%mod)+((b%mod*b%mod)%mod);
            a = c;
            b = d;
            */
            long c = (a%mod) * ( ((b%mod)*2) - (a%mod) + mod);
            long d = ((a%mod*a%mod)%mod)+((b%mod*b%mod)%mod);
            a = c % mod;
            b = d % mod;
            bit = m >> i;
            if ( (bit & 1) != 0 ) {
                long e = ((a % mod) + (b % mod)) % mod;
                a = b;
                b = e;
            }
        }
        return  a;
    }
}
