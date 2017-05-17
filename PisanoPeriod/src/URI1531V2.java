import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 02/05/2017.
 *
 * Para estudos sobre o algoritmo, pois a multiplicacao de matriz nao passa
 * no URI. Entender o PQ
 * 10% de erro
 */
public class URI1531V2 {

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
        return f[1][0];
    }

    private static long[][] multMatrixFibonacci (long f[][], long g[][], long mod) {
        long h[][] = new long[f.length][g[0].length];
        int c = f[0].length, l = g.length;
        for(int i=0; i<c; i++) {
            for(int j=0; j<l; j++) {
                for(int k=0; k<c; k++) {
                    long m = ((f[i][k]%mod) * (g[k][j]%mod))%mod;
                    h[i][j] = ((h[i][j]%mod)+(m%mod))%mod;
                }
            }
        }
        return h;
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
    // https://www.urionlinejudge.com.br/judge/pt/problems/view/1531
    // solver

    public static long solver(long n, long mod) {
        /**
         * Resolver
         * FIB(FIB(n)) % m
         * Fib(n)%m = Fib(n%c)%m
         * */
        long table[][] = new long[][]{{1,1},{1,0}};
        long constant [][] = new long[][]{{1,1},{1,0}};
        long cycle    = getPisanoPeriod2(mod);
        long i        = modularExpMatrix(n, table, constant, cycle);
        table         = new long[][]{{1,1},{1,0}};
        long j        = modularExpMatrix(i, table, constant, mod);
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

    public static void main(String[] args) {
        run();
    }
}
