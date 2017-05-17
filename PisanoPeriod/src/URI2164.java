/**
 * Created by C.Lucas on 07/05/2017.
 */



import static java.lang.Math.sqrt;

/**
 * https://www.urionlinejudge.com.br/judge/pt/problems/view/2164
 * */

public class URI2164 {

    /**
     * Formula de binet
     *
     * P = ((1+sqrt(5))/2)^N
     * Q = ((1-sqrt(5))/2)^N
     * Fib(N) ~ (P-Q)/sqrt(5)
     *
     * */

    public static double C = sqrt(5);
    public static double Q = (1.0+C)/2.0;
    public static double P = (1.0-C)/2.0;

    /**
     * TODO
     * IMplementa uma funcao de exponeciacao rapida
     * */
    public static double exp(double b, int e) {
        return 0.0;
    }

    public static double solver(int n) {
        double a = exp(Q, n);
        double b = exp(P, n);
        return (a-b)/C;
    }

}
