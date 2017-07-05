import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by r028367 on 21/06/2017.
 * https://www.hackerrank.com/challenges/maximum-perimeter-triangle?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=3-day-campaign
 * http://www.mathcentre.ac.uk/resources/uploaded/mc-ty-triangleformulae-2009-1.pdf
 * http://www2.clarku.edu/~djoyce/trig/laws.html
 */
public class MPT {

    /**
     * https://en.wikipedia.org/wiki/Triangle_inequality
     * */
    public static boolean testSide(long a, long b, long c) {
        return (a+b) > c && (a-b) < c;
    }

    public static boolean testSide2(long a, long b, long c) {
        long max = Math.max(Math.max(a, b), c);
        return 2 * max < a + b + c;
    }

    public static boolean isDegenerate(long a, long b, long c) {
        double A = fxHeron(a, b, c);
        return A == 0.0;
    }

    public static double fxHeron(long a, long b, long c) {
        double S = semiperimeter(a, b, c);
        return Math.sqrt(S * (S-a) * (S-b) * (S-c));
    }

    public static double semiperimeter(long a, long b, long c) {
        return (a+b+c)/2.0;
    }

    public static boolean isNonDegTriangle(long a, long b, long c) {
        boolean sa = testSide2(a, b, c) ;
        boolean sb = isDegenerate(a, b, c);
        return sa && (! sb );
    }

    public static long ma, mb, mc;
    public static ArrayList<Long> edges;

    public static void solver() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int e = Integer.parseInt(bufferedReader.readLine());
            edges = new ArrayList<Long>();
            StringTokenizer tk = new StringTokenizer(bufferedReader.readLine(), " ");
            for(;tk.hasMoreTokens();e--) {
                edges.add(Long.parseLong(tk.nextToken()));
            }
            Collections.sort(edges);
            ma = mb = mc = Long.MIN_VALUE;
            boolean anyOne = false;
            for(int i=0; i<edges.size()-2; i++) {
                for (int j = i+1; j<edges.size()-1; j++) {
                    for (int k = j+1; k<edges.size(); k++) {
                        long a = edges.get(i);
                        long b = edges.get(j);
                        long c = edges.get(k);
                        if(isNonDegTriangle(a, b, c)) {
                            ma = a > ma ? a : ma;
                            mb = b > mb ? b : mb;
                            mc = c > mc ? c : mc;
                            anyOne = true;
                        }
                    }
                }
            }
            System.out.println(anyOne ? String.format("%d %d %d", ma, mb, mc) : -1);
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        solver();
/*
        System.out.println(isNonDegTriangle(1,2,3));
        System.out.println(isNonDegTriangle(3,4,5));
        System.out.println(isNonDegTriangle(4,3,5));
        System.out.println(isNonDegTriangle(5,4,3));
        System.out.println(isNonDegTriangle(15,12,9));
        System.out.println(isNonDegTriangle(1,1,1));
        System.out.println(isNonDegTriangle(1,3,3));
*/
    }
}
