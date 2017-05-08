import java.io.*;
import java.nio.CharBuffer;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 02/05/2017.
 * http://marathoncode.blogspot.com.br/2012/09/relacao-de-recorrencia-com.html
 * https://www.nayuki.io/page/fast-fibonacci-algorithms
 * http://crbonilha.com/pt/2014/06/03/implementacao-fast-doubling/
 * https://en.wikipedia.org/wiki/Exponentiation_by_squaring
 */
public class PisanoPeriod {

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

    // http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fibonacci/fibtable.html
    // AINDA NAO FUNCIONA
    private static long nthItFibonacciLogn(long p, long m) {
        //long I [][] = {{1,0}, {0,1}};
        long A [][] = { {1,1}, {1,0}};
        long B [][] = { {1,1}, {1,0}};
        for(long i=p; i>1; i >>= 1) {
            multiplyMatrixFibonacci(A, A, m);
            if( (i&1) == 1) {
                multiplyMatrixFibonacci(A, B, m);
            }
        }
        return A[0][1];
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


    public static void test() {
        /*
        for(long d=1; d<10000; d++) {
            System.out.printf("%d %d\n", d, nthRecFibonacciLogn(new long[][]{{1,1},{1,0}}, d, 3));
        }
        */
        for (long m=1; m<101; m++) {
            System.out.printf("%d %d\n", m, getPisanoPeriod(m));
        }
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
        long i        = nthRecFibonacciLogn(matA, n%cycle, mod);
        matA          = new long[][]{{1,1},{1,0}};
        long j        = nthRecFibonacciLogn(matA, i%cycle, mod);
        return j;
    }

    private static void test2() {
        System.out.println(solver(1,100));
        System.out.println(solver(2,100));
        System.out.println(solver(3,100));
        System.out.println(solver(4,100));
        System.out.println(solver(5,100));
        System.out.println(solver(5,2));
        System.out.println(solver(6,100));
    }

    public static void readerV1() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            CharBuffer charBuffer = CharBuffer.allocate(1 << 10);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
            while (bufferedReader.read(charBuffer) > 0) {
                charBuffer.flip();
                StringTokenizer tokenizer = new StringTokenizer(charBuffer.toString(), " ");
                long n = Long.parseLong(tokenizer.nextToken());
                long mod = Long.parseLong(tokenizer.nextToken());
                printWriter.printf("%d\n", solver(n, mod));
            }
        } catch (IOException ioex) {}
    }

    public static void readerV2() {
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

    public static void main(String[] args) {
        //test();
        //test2();
        readerV2();
    }
}
